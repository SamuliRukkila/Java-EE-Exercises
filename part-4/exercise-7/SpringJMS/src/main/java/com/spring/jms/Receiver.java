package com.spring.jms;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;

/**
 * Koska kyseessä on viestipohjainen sovellus, siihen
 * on luotava vastaanotin, joka käsittelee lähetettyjä
 * viestejä.
 * 
 * Alla oleva luokka on normaali POJO (Plain Old Java Object)
 * -luokka, joka määrittelee funktiolla menetelmän 
 * viestien vastaanottamiseen.
 * 
 * Tässä luokassa tarvittavien erilaisten Spring beanien
 * luominen ja konfigurointi on ryhmitelty ReceiverConfig-luokassa.
 */

public class Receiver {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(Receiver.class);
  
  
  /**
   * Mukavuuden lisäämiseksi tähän on lisätty olio
   * CountDownLatch. Tämän avulla luokka voi ilmoittaa
   * viestin vastaanottamisesti (käytetään yleensä
   * vain testivaiheessa).
   */
  private CountDownLatch latch = new CountDownLatch(1);

  public CountDownLatch getLatch() {
    return latch;
  }
  
  
  /**
   * @JmsListener -annotaatio luo viestien kuuntelemiseen
   * tarkoitetun containerin "kulissien takana" jokaiselle
   * annotoidulle funktiolle, käyttäen hyväkseen kirjastoa
   * JmsListenerContainerFactory. 
   * 
   * destination -elementillä määritellään tälle kuuntelijalle
   * kohteen. Tässä esimerkissä määränpää on "helloworld.q".
   * 
   * Funktio odottaa beania
   * nimeltään jmsListenerContainerFactory, joka on luotu
   * konfiguraatio-luokassa (SenderConfig.java).
   * 
   * @param message - Vastaanotettava viesti
   */
  @JmsListener(destination = "helloworld.q")
  public void receive(String message) {
    LOGGER.info("received message='{}'", message);
    latch.countDown();
  }
}