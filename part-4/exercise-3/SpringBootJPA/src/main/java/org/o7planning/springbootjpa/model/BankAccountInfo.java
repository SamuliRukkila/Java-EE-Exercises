package org.o7planning.springbootjpa.model;

/**
 * Kun Entity -luokka (JPA-luokka BankAccount.java) näyttää taulun
 * informaation, Model -luokka (tämä luokka) edustaa SQL -kysely-
 * lausekkeen palauttavan yhden tietueen tietoja (yhden tai 
 * useamman luokan).
 * 
 * Kun esim. @Repository -annotaation omaava luokka tekee kyselyn, käyttää
 * se beanina tietojen ylläpitämiseen sun muuhun tätä luokkaa
 * JPA-luokan sijasta (kun haluat esim. palautetan objektin ID:n tmv.).
 */
public class BankAccountInfo {
  
  private Long id;
  private String fullName;
  private double balance;
  
  public BankAccountInfo() {}
  
  // Käytetään JPA -kyselyissä
  public BankAccountInfo(Long id, String fullName, double balance) {
    this.id = id;
    this.fullName = fullName;
    this.balance = balance;
  }
  
  
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
