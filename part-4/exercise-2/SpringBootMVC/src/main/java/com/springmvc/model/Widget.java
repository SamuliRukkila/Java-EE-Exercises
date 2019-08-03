package com.springmvc.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Mistä komponenteista muodostuvat model, view ja controller?
 * -----------------------------------------------------------
 * = MODEL
 * -------
 * 
 * Kyseinen luokka on MVC -mallissa Model, koska se edustaa 
 * meidän objektia/objekteja. Tämä on hyvin simppeli model, joka
 * sisältää 3 attribuuttia.
 * 
 * Tämä luokka säilytetään omana taulunaan H2 -kirjaston tietokannassa.
 * Käytämme CrudRepository -rajapintaa tehdäksemme erilaisia 
 * tietokanta -kyselyitä tähän tauluun ilman, että joudumme kirjoittamaan
 * niitä itse koodiin. 
 *
 * Kun näkymä (view) pyytää esim. tiettyjä objekteja lataamalla tietyn sivun,
 * ottaa se HTTP-pyynnöllä yhteyden kontrolleriin, joka suorittaa tarvittavat 
 * jatko-kyselyt (käyttäen valmista CrudRepositoryä tässä tapauksessa) ja palauttaa
 * sen, mitä Model palauttaa.
 * 
 * @Entity identifikoi "Widget" -instanssin entiteettinä, jota voidaan säilyttää tietokannssa
 */
@Entity
public class Widget {
  
  // Pää-avain, AUTO_INCREMENT -arvoinen, joten käyttäjän ei tarvitse itse antaa ID:tä
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String name;
  private String description;
  
  public Widget() {}
  
  public Widget(Long id, String name, String description) {
      this.id = id;
      this.name = name;
      this.description = description;
  }
  
  
  /* GETTERS & SETTERS ATTRIBUUTEILLE */
  
  public Long getId() {
      return id;
  }
  public void setId(Long id) {
      this.id = id;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
  public String getDescription() {
      return description;
  }
  public void setDescription(String description) {
      this.description = description;
  }
  
}
