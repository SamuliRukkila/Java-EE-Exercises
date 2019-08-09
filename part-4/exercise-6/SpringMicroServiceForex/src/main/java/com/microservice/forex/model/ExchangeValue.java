package com.microservice.forex.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Normaali JPA:n luokka, joka toimii entitynä. Tarjoaa
 * valuutanvaihtoon tarvittavat attribuutit sekä metatiedot,
 * kuten portti, jota käytetään devausvaiheessa.
 * 
 * Projektissa on mukana H2-tietokantakirjasto, jonka ansioista
 * meidän ei itse tarvitse tuottaa tietokantaa tätä demoa varten.
 * 
 * Projekti sisältää myös valmiin SQL-skriptin mikä luo valmiiksi
 * valuutanvaihto-kursseja.
 */

@Entity
public class ExchangeValue {

  @Id
  private Long id;
  
  @Column(name = "currency_from")
  private String from;
  
  @Column(name = "currency_to")
  private String to;
  
  private BigDecimal conversionMultiple;
  private int port;
  
  
  /* KONSTRUKTORIT */
  
  public ExchangeValue() {}
  
  public ExchangeValue(Long id, String from, String to, BigDecimal conversionMultiple) {
    super();
    this.id = id;
    this.from = from;
    this.to = to;
    this.conversionMultiple = conversionMultiple;
  }
  
  
  /* SETTERIT JA GETTERIT */
  
  public Long getId() {
    return id;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public BigDecimal getConversionMultiple() {
    return conversionMultiple;
  }
  
  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  
}
