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
  
  // Objects which we'll use with JPA to perform queries, updates etc
	private static EntityManagerFactory emf;
	private static EntityManager em;
	private static EntityTransaction tx;
	
	static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	/**
	 * Init's entity manager according to same manager-factory in persistence.xml, where it 
	 * has been created.
	 * @throws Exception Throws error if cannot init entity-manager
	 */
	public static void initEntityManager() throws Exception {
		emf = Persistence.createEntityManagerFactory("JPAsimplekoePU");
		em = emf.createEntityManager();
	}
	
	/**
	 * Closes all JPA -connections to MySQL
	 */
	public static void closeEntityManager() {
		em.close();
		emf.close();
	}
	
	/**
	 * Starts transaction. After transaction has been established, queries can be executed.
	 * Transaction -initialization -object will be placed to global variable called "tx"
	 */
	public static void initTransaction() {
		tx = em.getTransaction();
	}
	
	
	/**
	 * Prints all book-groups from MySQL. Function will use ready-made @NamedQuery to print all the 
	 * available book-groups with their ID and name. User will then be prompted to select one of the
	 * book-groups by their unique ID for further querying.
	 * @return 1 book-group's unique ID | zero if input cannot be parsed to integer
	 */
	public static int printBookGroups() {
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
    }	catch (IOException ioe) {
      return 0;
    }
	}
	
	
  /**
   * Prints all books from MySQL. Function will use ready-made @NamedQuery to print all the 
   * available books with their ID, author and title. User will then be prompted to select one of the
   * books by their unique ID for further querying.
   * @return 1 book's unique ID | zero if input cannot be parsed to integer
   */
	public static int printBooks() {
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
	  } catch (IOException ioe) {
	    return 0;
	  }
	}
	
	/**
	 * Create new book. Function which will create a new book to MySQL's book-table.
	 * User input will be used to determine the information for the book. All the values
	 * will be checked so that the book's values will be valid. Book's book-group will be
	 * determined via another function. If all the values are valid, push new book to the table.
	 */
	public static void createBook() {
	  
	  String author = null;
	  String title = null;
	  
	  // Get user's input for new book's information
	  try {
	    System.out.print("Kirjan tekijän nimi: ");
	    author = reader.readLine();
	    System.out.print("Kirjan nimi: ");
	    title = reader.readLine();
	  } catch (IOException ioe) {
	    System.out.println(ioe);
	  }
		
	  // Create objects from book and book-group
	  Book b = new Book();
		BookGroup bg = new BookGroup();
		
		// If author's and title's values aren't empty, proceed
		if (!author.replaceAll("\\s+", "").isEmpty() && !title.replaceAll("\\s+", "").isEmpty()) {
		  // Select book-group for the book
		  Integer bookGroupId = printBookGroups();
		  
		  // If user selected atleast one (viable) book-group for the book via ID
		  if (bookGroupId != 0 && bookGroupId != null) {
		    bg.setId(bookGroupId);
		    b.setBookGroup(bg);
		  }
		  
		  // Push new values into a book-object
		  b.setAuthor(author);
		  b.setTitle(title);
		  
		  initTransaction();
		  
		  // Begin query and insert new values to MySQL -table with ORM
		  tx.begin();
		  em.persist(b);
		  tx.commit();
		}
	}
	

	/**
	 * Delete's book from table via it's ID. Book will first be chosen via
	 * another function. If the ID-value isn't empty, it'll be deleted.
	 */
	public static void deleteBook() {
	  // Pick the ID of the book from another function and place it into variable
	  Integer id = printBooks();
	  // If the value is viable and not empty
	  if (id != 0 && id != null) {
	    // Find the book from book-class via unique ID -value
	    Book b = em.find(Book.class, id);
	    try {
	      // Begin query and delete book from MySQL -table with ORM
	      initTransaction();
	      tx.begin();
	      em.remove(b);
	      tx.commit();
	    } catch(IllegalArgumentException iae) {
	      System.out.println("\n// Kirjaa ID:llä: [" + id + "] ei voitu poistaa. Ei löydetty\n");
	    }
	  }
	}
	
	
	/**
	 * Creates new book-group. It'll be created via user input. If name is valid and isn't empty
	 * it'll be pushed into MySQL -table.
	 */
	public static void createBookGroup() {
	  
	  String name = null;
	  System.out.print("Anna kirjaryhmälle nimi: ");
	  
	  // Fetch the new name via user input
	  try {
	    name = reader.readLine();
	  } catch (IOException ioe) {
	    System.out.println(ioe);
	  }
	  
	  // If new name is valid and isn't empty
	  if (name != null && !name.replaceAll("\\s+", "").isEmpty()) {
	    
	    BookGroup bg = new BookGroup();
	    bg.setName(name);
	    
	    initTransaction();
	    tx.begin();
	    em.persist(bg);
	    tx.commit();
	  }
	}
	
	
	/**
	 * Deletes book-group and all the books which are attached to that book-group.
	 * First the wanted book-group's ID will be fetched via external function. If the 
	 * value is valid and not empty, find all the books which has the same book-group's ID
	 * attached to them. Then through a for-loop delete them one-by-one. Lastly delete
	 * the book-group.
	 */
	public static void deleteBookGroup() {
	  
	  System.out.println("\n!! VAROITUS: Poistaa myös kirjaryhmään kuuluvat kirjat! !!\n");
	  // Get the wanted book's ID via external function and place it into a variable
	  Integer id = printBookGroups();
	  
	  // If the value is viable
	  if (id != 0 && id != null) {
	    try {
	      // Find the row via ID
	      BookGroup bg = em.find(BookGroup.class, id);
	      // Fetch all the books which has the same book-group ID attached to them
	      List<Book> books = emf.createEntityManager().createNamedQuery("Book.findByBookGroup").setParameter("id", bg).getResultList();

	      initTransaction();
	      tx.begin();
	      
	      // Loop all the founded books. Find their object's and them remove them via em -object
	      for (Book book: books) {
	        Book b = em.find(Book.class, book.getId());
	        em.remove(b);
	      }
	      // Lastly remove the book-group
	      em.remove(bg);
	      // Commit changes
	      tx.commit();
	      
	    } catch (IllegalArgumentException iae) {
	      System.out.println(iae);
	      System.out.println("\n// Kirjaryhmää ID:llä: [" + id + "] ei voitu poistaa. Ei löydetty\n");
	    }
	  }
	}
	 
	
	/**
	 * Finds all the books from the table and print them into a table -structure.
	 */
  public static void showBooks() {
    
    // Fetch all the books
		List<Book> books = emf.createEntityManager().createNamedQuery("Book.findAll").getResultList();
		System.out.println("+---------------+\n" +
		                   "|    KIRJAT     |");
		
		// Column names
		String leftAlignFormat = "| %-30s | %-40s | %-40s |%n";
		System.out.format("+---------------+----------------+------------------------------------------+------------------------------------------+%n");
		System.out.format("| Kirjailija                     | Kirja                                    | Kirjaryhmä                               |%n");
		System.out.format("+--------------------------------+------------------------------------------+------------------------------------------+%n");
		
		// Loop through all books the print them row-by-row
		for (Book b : books) {
		  System.out.format(leftAlignFormat, b.getAuthor(), b.getTitle(), 
		    b.getBookGroup() != null ? b.getBookGroup().getName() : "");
		}
		
		// Prints the amount of books in the table
		System.out.format("+--------------------------------+------------------------------------------+------------------------------------------+%n");
		System.out.format("| KIRJOJEN MÄÄRÄ                 | " + books.size() + "                                                                                  |%n");
		System.out.format("+--------------------------------+-------------------------------------------------------------------------------------+%n");
		
		// Print the columns for book-groups' names and the amount of books in each of the group
		System.out.println("+---------------+\n" +
		                   "|  KIRJARYHMÄT  |");
		leftAlignFormat = "| %-30s | %-15s |%n";
		System.out.format("+---------------+----------------+-----------------+%n");
		System.out.format("| Nimi                           | Kirjoja (kpl)   |%n");
		System.out.format("+--------------------------------+-----------------+%n");
		
		// Fetch all the book-groups
		List<Object[]> bookGroups = emf.createEntityManager().createNamedQuery("BookGroup.findAndCount").getResultList();
		// Loop through all book-groups to print them row-by-row
		for (Object[] res: bookGroups) {
		  System.out.format(leftAlignFormat, (String) res[0], ((Number) res[1]).intValue());
		}
		System.out.format("+--------------------------------+-----------------+%n");
	}

	
  /**
   * Main-function which'll be executed when program starts. This a while -loop program so it loops through the same 
   * block as many times as the user wants.
   */
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
	          System.out.print("--- Sovellus suljettu. Näkemisiin ---");
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
