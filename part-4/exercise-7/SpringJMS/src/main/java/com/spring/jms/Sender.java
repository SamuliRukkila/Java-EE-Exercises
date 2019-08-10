package com.spring.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;

/**
 * Kyseinen luokka lähettää helloworld.q -viestin ActiveMQ:lle - käyttäen
 * lähetykseen JMS:ää.
 * 
 * Viestien lähettämisessä käytämme JmsTemplate-rajapintaa, joka
 * vaatii viittauksen ConnectionFactory -rajapintaan. Templaatti
 * tarjoaa metodeja, jotka käsittelevät resurssien luomista & 
 * vapauttamista, kun viestejä lähetetään/vastaanotetaan synkronisesti.
 * 
 * JmsTemplaten ja Senderin luonti hoidetaan SenderConfig -luokassa.
 * 
 */
public class Sender {

  private static final Logger LOGGER =
      LoggerFactory.getLogger(Sender.class);
  
  /**
   * JmsTemplate on automaattisesti injektoitu (@Autowired)
   * tähän luokkaan, koska beanin todellinen luominen tehdään
   * erillisessä luokassa (SenderConfig.java).
   */
  @Autowired
  private JmsTemplate jmsTemplate;
  
  /**
   * Funktio, joka lähettää annetun objektin "helloworld.q" 
   * kohteeseen, samalla kääntäen objektin JMS-viestiksi.
   * 
   * Se. minkä tyyppinen JMS-viesti luodaan, riippuu siitä
   * minkä tyyppinen objekti sille annetaan. 
   * 
   * @example - String -> JMS TextMessage
   * @param message - Viesti, joka lähetetään ActiveMQ:lle
   */
  public void send(String message) {
    LOGGER.info("sending message='{}'", message);
    jmsTemplate.convertAndSend("helloworld.q", message);
  }
}