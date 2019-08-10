package com.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring tarjoaa JMS (Java Message Service)-integrointikehyksen,
 * joka yksinkertaistaa JMS-API:n käytön.
 * 
 * Tässä tutoriaalissa luodaan Hello World -esimerkki, jossa
 * lähetämme/vastaanotamme viestin Apache ActiveMQ:lle/ActiveMQ:lta
 * käyttäen Spring JMS:ää, Spring Boottia sekä Mavenia.
 * 
 * Viestien lähettämiseen käytämme tavallista luokkaa Sender.java ja 
 * sen konfiguraatioluokkaa SenderConfig.java. Konfiguraatioluokka
 * määrittelee beanit sekä yhteyden ActiveMQ:een.
 * 
 * Viestin vastaanottamiseen käytämme tavallista luokkaa Receiver.java
 * ja sen konfiguraatioluokkaa ReceiverConfig.java. Konfiguraatioluokka
 * määritellee beanit sekä yhteyden ActiveMQ:een.
 * 
 * 
 * Viestien lähettämistä sekä vastaanottamista voidaan testata laittamalla
 * tämä ohjelma päälle ajamalla se Java Application-suorituksena tai 
 * ajamalla sille tehty yksinkertainen testi-tiedosto (mvn test).
 */

@SpringBootApplication
public class SpringJmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringJmsApplication.class, args);
	}

}
