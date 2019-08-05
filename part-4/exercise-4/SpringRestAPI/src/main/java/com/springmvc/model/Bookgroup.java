package com.springmvc.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * JPA-class which is specified as an entity. It's 
 * like the blueprint for the MySQL-table "bookgroup".
 * Enables ORM-queries in this application.
 * 
 * We'll specify exact SQL-table's name with the
 * @Table -annotation.
 * 
 * @author samuli
 */
@Entity
@Table(name = "bookgroup")
public class Bookgroup implements Serializable {
  
  private static final long serialVersionUID = 1L;
  
  /**
   * Primary key for the "bookgroup" -table. Enables
   * AUTO_INCREMENT -attribute for automatic values.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "bookgroup_id")
  private Integer id;
  
  /* OTHER TABLE ATTRIBUTES */
  
  @Column(name = "name")
  private String name;

  
  /* CONSTRUCTORS */
  
  public Bookgroup() {}

  public Bookgroup(String name) {
    this.name = name;
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

}
