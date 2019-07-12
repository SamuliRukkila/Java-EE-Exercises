/**************
  
JPAsimplekoe.java: yksinkertainen JPA-esimerkki joka ei siis ole 
web-sovellus vaan tavallinen merkkipohjainen Java-sovellus joka
suoritetaan konsolissa. Sovellus lisää book-tauluun Book -tyyppisen olion
ja tulostaa konsoliin taulussa olevat kirjat sekä niiden lukumäärän
  
Luodaan yksinkertainen JPA-yhteys MySQL-kantaan. 
Homma tehdään tavallisessa Java-projektissa 
ilman JavaEE-sovelluspalvelinta.
  
Luo seuraavanlainen taulu kantaan jeedb1:

   CREATE TABLE `book` (
  `id` int(11) NOT NULL auto_increment,
  `title` varchar(50) NOT NULL,
  `author` varchar(50) NOT NULL,
   PRIMARY KEY  (`id`)
   ) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=0;
   
Book.java on entiteettiluokka josta luodaan book-oliot. Olio vastaa tietokannan
book-taulun tietuetta.
 
JPAsimplekoe.java on java-luokka joka käyttää JPA-yhteyttä olioiden tallentamiseen
kantaan ja hakemiseen kannasta. book-oliot menevät book-tauluun ja ne haetaan 
haetaan sieltä NamedQuery-kyselyllä.
  
Persistence.xml on JPA-yhteyden konfiguraatiotiedosto jossa on määritelty yhteyden nimi eli 
persistence unit joka on JPAsimplekoePU. Lisäksi siellä ovat tietokantayhteyden tiedot.

 *****************/
package jpaclasses;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

@SuppressWarnings("unchecked")
public class JPAsimplekoe {
  
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public static void initEntityManager() throws Exception {
		emf = Persistence.createEntityManagerFactory("JPAsimplekoePU");
		em = emf.createEntityManager();
	}

	public static void closeEntityManager() {
		em.close();
		emf.close();
	}

	public static void initTransaction() {
		tx = em.getTransaction();
	}
	
	public static int printBookGroups() throws IOException {
    List<BookGroup> results = emf.createEntityManager().createNamedQuery("BookGroup.findAll").getResultList();
	  System.out.println("Valitse kirjaryhmä numerolla:");
	  for (BookGroup bg : results) {
	    System.out.println("(" + bg.getId() + ") | "+ bg.getName() +" ");
	  }
	  try {
	    System.out.print("> ");
      return Integer.parseInt(reader.readLine());
    } catch (NumberFormatException fe) {
      return 0;
    }	    
	}
	
	public static int printBooks() throws IOException {
	  List<Book> res = emf.createEntityManager().createNamedQuery("Book.findAll").getResultList();
	  System.out.println("Valitse kirja numerolla:");
	  for (Book b : res) {
	    System.out.println("(" + b.getId() + ") | " + b.getAuthor() + ": " + b.getTitle());
	  }
	  try {
	    System.out.print("> ");
	    return Integer.parseInt(reader.readLine());
	  } catch (NumberFormatException fe) {
	    return 0;
	  }
	}
	
	public static void createBook() throws Exception {
	  
	  System.out.print("Kirjan tekijän nimi: ");
	  String author = reader.readLine();
	  System.out.print("Kirjan nimi: ");
	  String title = reader.readLine();
		
	  Book b = new Book();
		BookGroup bg = new BookGroup();
		
		if (!author.replaceAll("\\s+", "").isEmpty() && !title.replaceAll("\\s+", "").isEmpty()) {
		  Integer bookGroupId = printBookGroups();
		  
		  if (bookGroupId != 0 && bookGroupId != null) {
		    bg.setId(bookGroupId);
		    b.setBookGroup(bg);
		  }
		  
		  b.setAuthor(author);
		  b.setTitle(title);
		  
		  initTransaction();
		  
		  tx.begin();
		  em.persist(b);
		  tx.commit();
		}
	}
	
	
	public static void deleteBook() throws Exception {
	  Integer id = printBooks();
	  if (id != 0 && id != null) {
	    Book b = em.find(Book.class, id);
	    try {
	      initTransaction();
	      tx.begin();
	      em.remove(b);
	      tx.commit();
	    } catch(IllegalArgumentException iae) {
	      System.out.println("\n// Kirjaa ID:llä: [" + id + "] ei voitu poistaa. Ei löydetty\n");
	    }
	  }
	}
	
	public static void createBookGroup() throws Exception {
	  System.out.print("Anna kirjaryhmälle nimi: ");
	  String name = reader.readLine();
	  if (name != null && !name.replaceAll("\\s+", "").isEmpty()) {
	    BookGroup bg = new BookGroup();
	    bg.setName(name);
	    
	    initTransaction();
	    tx.begin();
	    em.persist(bg);
	    tx.commit();
	  }
	}
	
	public static void deleteBookGroup() throws Exception {
	  System.out.println("\n**VAROITUS: Poistaa myös kirjaryhmään kuuluvat kirjat!**\n");
	  Integer id = printBookGroups();
	  if (id != 0 && id != null) {
	    try {
	      BookGroup bg = em.find(BookGroup.class, id);
	      BookGroup bb = new BookGroup();
	      bb.setId(id);
	      initTransaction();
	      List<Book> books = emf.createEntityManager().createNamedQuery("Book.findByBookGroup").setParameter("id", bg).getResultList();
	      tx.begin();
	      
	      for (Book book: books) {
	        Book b = em.find(Book.class, book.getId());
	        em.remove(b);
	      }
	      
	      em.remove(bg);
	      tx.commit();
	    } catch (IllegalArgumentException iae) {
	      System.out.println(iae);
	      System.out.println("\n// Kirjaryhmää ID:llä: [" + id + "] ei voitu poistaa. Ei löydetty\n");
	    }
	  }
	}
	
  public static void showBooks() throws Exception {

		List<Book> res1 = emf.createEntityManager().createNamedQuery("Book.findAll").getResultList();
		System.out.println("+---------------+\n" +
		                   "|    KIRJAT     |");
		
		String leftAlignFormat = "| %-20s | %-30s | %-30s |%n";
		System.out.format("+---------------+------+--------------------------------+--------------------------------+%n");
		System.out.format("| Kirjailija           | Kirja                          | Kirjaryhmä                     |%n");
		System.out.format("+----------------------+--------------------------------+--------------------------------+%n");
		
		for (Book b : res1) {
		  System.out.format(leftAlignFormat, b.getAuthor(), b.getTitle(), 
		    b.getBookGroup() != null ? b.getBookGroup().getName() : "");
		}
		
		System.out.format("+----------------------+--------------------------------+--------------------------------+%n");
		System.out.format("| KIRJOJEN MÄÄRÄ       | " + res1.size() + "                                                              |%n");
		System.out.format("+----------------------+-----------------------------------------------------------------+%n");
		
		System.out.println("+---------------+\n" +
		                   "|  KIRJARYHMÄT  |");
		
		System.out.format("+---------------+----------------+-----------------+%n");
		System.out.format("| Nimi                           | Kirjoja (kpl)   |%n");
		System.out.format("+--------------------------------+-----------------+%n");
		
		leftAlignFormat = "| %-30s | %-15s |%n";
		List<Object[]> res2 = emf.createEntityManager().createNamedQuery("BookGroup.findAndCount").getResultList();
		for (Object[] res: res2) {
		  System.out.format(leftAlignFormat, (String) res[0], ((Number) res[1]).intValue());
		}
		System.out.format("+--------------------------------+-----------------+%n");
	}

	
	public static void main(String args[]) {
	  
	  boolean run = true;
	  
	  System.out.println("===============================\n" +
	                     "Kirjojen & kirjaryhmien työkalu\n" +
	                     "===============================");
	  
	  while (run == true) {
	    System.out.println("\nMitäs tehdään?\n--------------\n" +
              	         "1) Lisää kirja \n2) Poista kirja \n" +
              	         "3) Lisää kirjaryhmä \n4) Poista kirjaryhmä \n" +
              	         "5) Näytä kirjat\n6) Poistu");
	    System.out.print("> ");

	    try {
	      String input = reader.readLine();
	      initEntityManager();
	      initTransaction();
	      switch(input) {
	        case "1":
	          createBook();
	          break;
	        case "2":
	          deleteBook();
	          break;
	        case "3":
	          createBookGroup();
	          break;
	        case "4":
	          deleteBookGroup();
	          break;
	        case "5":
	          showBooks();
	          break;
	        case "6":
	          System.out.println("Suljetaan..");
	          run = false;
	        default:
	          continue;
	      }
	      closeEntityManager();
	    } catch (Exception e) {
	      System.out.println(e);
	    }
	  }
	}

}
