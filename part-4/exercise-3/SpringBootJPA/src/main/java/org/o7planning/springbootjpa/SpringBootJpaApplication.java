/**
 * Mitä sovelluksessa tapahtuu
 * ---------------------------
 * 
 * Sovelluksessa demotaan yksinkertaista tapausta, jossa tarvitsisit
 * transaktion -menetelmää tietokantakyselyissä. Sovellus käyttää
 * Spring MVC -menetelmää, jossa on omat kerrokset. 
 * 
 * Sovelluksessa demotaan pankkitilien välistä rahansiirtoa. Ja sitä,
 * kuinka asia voidaan hoitaa kuntoon myös virhetilanteissa (rahaa siirretään,
 * mutta rahaa poistattaessa tulee virhe ja toinen ei menetä rahaa yhtään tmv.).
 * 
 * Tässä sovelluksessa on käytetty hyväksi Springin @Transactional -menetelmää,
 * jonka avulla DAO-luokka voi pyyhkiä tehdyt muutokset jos tulee virhetilanteita,
 * näin minimoiden samalla riskit.
 */

package org.o7planning.springbootjpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootJpaApplication.class, args);
	}

}
