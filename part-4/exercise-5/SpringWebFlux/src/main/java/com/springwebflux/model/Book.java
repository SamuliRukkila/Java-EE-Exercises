package com.springwebflux.model;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Dokumentti MongoDB-tietokannalla. Kertoo dokumentin struktuurin.
 * 
 * @Document -annotaatio, jolla kerrotaan, että kyseinen luokka
 * on MongoDB:n dokumentti eli se tunnistaa MongoDB:lle pysyvän 
 * verkkotunnusobjektin.
 * 
 * @author samuli
 */
@Document
public class Book {
  
  private String id;
  private String title;
  private String author;
  
  
  /* CONSTRUCTORS */
  
  public Book() {}
  
  public Book(String id, String title, String author) {
    this.id = id;
    this.title = title;
    this.author = author;
  }
  

  /* SETTERS AND GETTERS FOR CLASS ATTRIBUTES */ 
  
  public String getId() {
    return id;
  }
  public void setId(String id) {
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
  

  
  /* CLASS FUNCTIONS LIKE toString(), equals() AND hashcode() */
  
  @Override
  public String toString() {
    return "Book [id=" + id + ", title=" + title + ", author=" + author + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((author == null) ? 0 : author.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
    result = prime * result + ((title == null) ? 0 : title.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Book other = (Book) obj;
    if (author == null) {
      if (other.author != null)
        return false;
    } else if (!author.equals(other.author))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    if (title == null) {
      if (other.title != null)
        return false;
    } else if (!title.equals(other.title))
      return false;
    return true;
  }
}
