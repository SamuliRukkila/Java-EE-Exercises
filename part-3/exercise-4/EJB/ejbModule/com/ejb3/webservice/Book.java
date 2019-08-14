/* Luokka josta Book-oliot syntyvät muistiin. Oliot laitetaan
 * Bookservicessä Arraylistiin joka toimii "valetietokantana"
 * Jos haluttaisiim käyttää oikeaa tietokantaa, voitaisiin 
 * tästä tehdä Entity -luokka ja käyttää tietokantaa JPA:n
 * välityksellä aivan kuten JPA-esimerkeissä.
 *  
 */

package com.ejb3.webservice;

import java.io.Serializable;

public class Book implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String title;
	private String author;
	
	
	/* KONSTRUKTORIT */
	
	public Book() {
	}

	public Book(Integer id) {
		this.id = id;
	}

	public Book(Integer id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}
	
	
	/* GETTERIT & SETTERIT */
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

}
