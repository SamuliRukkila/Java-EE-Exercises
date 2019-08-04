package com.springmvc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "book")
public class Book implements Serializable {

  private static final long serialVersionUID = 1L;
  
  @ManyToOne
  @JoinColumn(name = "bookgroup_id", nullable = false)
  private Bookgroup bg;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "book_id")
  private Integer id;
  
  @Column(name = "title")
  private String title;
  
  @Column(name = "author")
  private String author;
  
  
  /* CONSTRUCTORS */
  
  public Book() {}
  
  public Book(String title, String author) {
    this.title = title;
    this.author = author;
  }

  public Book(Bookgroup bookgroup, String title, String author) {
    this.bg = bookgroup;
    this.title = title;
    this.author = author;
  }
  
  
  /* SETTERS AND GETTERS FOR ATTRIBUTES */ 
  
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Bookgroup getBookgroup() {
    return this.bg;
  }

  public void setBookgroup(Bookgroup bg) {
    this.bg = bg;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

}
