package org.o7planning.springbootjpa.exception;

/**
 * Virheluokka, jossa virhe tulee luokan ainoaan funktioon. Kyseinen 
 * virhe-viesti tulostetaan käyttäjälle. Luokka käyttää laajentamiseen
 * yleistä Exception-luokkaa, jonka avulla viesti lisätään (super).
 *
 */
public class BankTransactionException extends Exception {

  private static final long serialVersionUID = -3128681006635769411L;
  
  public BankTransactionException(String msg) {
    super(msg);
  }
}
