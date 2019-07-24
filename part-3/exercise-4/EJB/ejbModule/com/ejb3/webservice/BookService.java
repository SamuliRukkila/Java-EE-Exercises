/* BookService tuottaa "valetietokannan" eli ArrayListin
 * muistiin. REST-Api pystyy käyttämään muistissa olevaa
 * ArrayListia jolloin Apia voi testata ilman että tarvitaan
 * oikeaa tietokantaa.
 * 
 * Jos käytettäisiin oikeaa kantaa, pitäisi BookServicen
 * metodeissa suorittaa tietokantatoimenpiteet
 */

package com.ejb3.webservice;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless // tilaton ejb
@LocalBean
public class BookService {

	// @PostConstruct -annotaatio pakottaa suorittamaan initiate -metodin heti kun
	// BookService -luokka on injektoitu
	@PostConstruct
	public void initiate() {
		books.add(new Book(1, "Kansalaisen Keittokirja", "Väinö Pannu"));
		books.add(new Book(2, "Avaruuden Limanuljaskat", "John Johnson"));
		books.add(new Book(3, "Jännitystä Jyvässeudulla", "Auvo Jyväjemmari"));
	}

	private List<Book> books = new ArrayList<>(); // luodaan "valetietokanta"

	// get all
	public List<Book> getAllBooks() {
		return books;
	}

	// get by id
	public Book getBookById(int id) {
		return books.get(id - 1);// haetaan kirja indeksillä joka on id - 1
	}

	// post
	public Book saveBook(Book book) {
		books.add(book);
		return book;
	}

	// delete
	public Book deleteBook(int id) {
		for (Book book : books) { // käydään läpi ArrayList
			if (book.getId().compareTo(id) != -1) {// etsitään poistettava kirja
				books.remove(book);
				return book;// palautetaan poistettu kirja
			}
		}
		return null;// jos ei löydy, palautetaan null
	}

	// put
	public Book updateBook(int id, Book book) {
		for (Book b : books) {
			if (b.getId().compareTo(id) != -1) {
				books.set(id - 1, book);
				return book;// palautetaan muokattu kirja
			}
		}
		return null;// jos ei löydy, palautetaan null
	}

}
