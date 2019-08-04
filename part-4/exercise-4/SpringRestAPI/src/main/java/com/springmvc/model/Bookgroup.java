package com.springmvc.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Bookgroup implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bookgroup_id")
  private Integer id;
  
  @Column(name = "name")
  private String name;

  @OneToMany(mappedBy = "bookgroup")
  private Set<Book> books = new HashSet<Book>(0);

  
  /* CONSTRUCTORS */
  
  public Bookgroup() {}

  public Bookgroup(String name) {
    this.name = name;
  }

  public Bookgroup(String name, Set<Book> books) {
    this.name = name;
    this.books = books;
  }
  
  
  /* SETTERS AND GETTERS FOR ATTRIBUTES */
  
  public Integer getId() {
    return this.id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Set<Book> getBooks() {
    return this.books;
  }

  public void setBooks(Set<Book> books) {
    this.books = books;
  }
}
