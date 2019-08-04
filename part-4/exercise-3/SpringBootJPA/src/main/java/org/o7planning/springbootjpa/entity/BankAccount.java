package org.o7planning.springbootjpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Kyseessä on JPA (tai Hibernate) -luokka, joka kokonaisuudessaan
 * vastaa yhtä tietokannan taulua. Tämän luokan attribuutit vastaavat 
 * taulukon sarakkeita. Luokka siis kertoo Springille minkälaisista
 * arvoista kyseinen taulu koostuu, jotta se osaa tehdä oikeanlaisia
 * ORM -kyselyitä muilla luokilla.
 * 
 * Tämä @Entity -annotaation omaava luokka, eli se edustaa taulukon tietueen
 * tietoja. Lisätietoina käytämme @Table(name) -annotaatiota, jotta Spring
 * osaa etsiä "bank_account" -nimistä taulua "bankAccount" -nimen sijaan.
 */
@Entity
@Table(name = "bank_account")
public class BankAccount {
  
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private Long id;
  
  @Column(name = "full_name", length = 128, nullable = false)
  private String fullName;
  
  @Column(name = "balance", nullable = false)
  private double balance;
  
  
  /* SETTERIT JA GETTERIT LUOKAN ATTRIBUUTEILLE */
  
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public double getBalance() {
    return balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }
  
  
}
