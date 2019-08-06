package com.springwebflux;

/**
 * Kuinka Spring WebFlux toimii
 * ----------------------------
 * 
 * WebFlux on täysin estämätön (non-blocking) reaktiivinen kehys. Se käyttää
 * Project Reactor -kirjastoa ja sen kustantajia (publishers) Flex ja Mono.
 * 
 * Pohjimmiltaan tilaaja (subscriber) luo tilauksen (subscription) julkaisijalle
 * (publisher), ja kun julkaisijalla on tietoja, se lähettää tapahtuman (event) 
 * tilaajalle elementti-streamina. 
 * 
 * Springin WebFluxissa kutsut reaktiivisia kirjastoja, jotka palauttavat monon tai
 * fluxin. Kontrollerit palauttavat kutsujalle monon tai fluxen. Koska nämä tyypit
 * palautuvat heti, kontrollerit luopuvat tehokkaasti prosesseistaan ja sallivat 
 * Reactorin käsitellä vastauksia asynkronisesti. Eli samalla kun vastaus on tulossa,
 * käyttäjä voi lähettää myös toisia kyselyitä eikä hänen tarvitse odotella vastausta.
 * 
 * WebFluxissa käytetään kahta kustantajaa (publishers):
 * MONO - Palauttaa 0 tai 1 elementin
 * FLUX - Palauttaa 0 tai enemmän elementtejä. Flux voi olla loputon,
 *        mikä tarkoittaa, että se voi palauttaa elementtejä loputtomasti tai
 *        se voi palauttaa elementtijonon ja lähettää sitten notifikaation, joka
 *        kertoo, että kaikki elementit ollaan palautettu.
 * 
 * Kun kutsut funktiota, joka palauttaa toisen yllämainituista
 * arvoista, se palauttaa sen välittömästi. Tulokset funktion kutsusta palautetaan 
 * mono- tai fluxe -palautusmuodossa kun ne tulevat saataville.
 * 
 * 
 * Mitkä ovat sovelluksen eri tiedostojen roolit? 
 * ----------------------------------------------
 * 
 * Book
 *  => Malliluokka, joka edustaa kirjaa palvelumme sisällä
 * BookRepository
 *  => Spring Data MongoDB -rajapinta, joka kertoo Spring Datalle pysyvyyskoodin (persistance code) 
 *     luomisesta kirjoille MongoDB:een ja MongoDB:stä
 * BookService (ja BookServiceImpl)
 *  => Business -luokan palvelu, jota käytetään vuorovaikutuksessa BookRepositoryn kanssa.
 *     Jatkaa kirjojen lähettämistä MongoDB:lle ja MongoDB:stä
 * BookController
 *  => Webin kontrolleri, joka vastaanottaa verkkopyyntöjä (web requests) ja
 *     ja palauttaa reaktiivisia vastauksia (Mono/Flex).
 * 
 * 
 * Miten Spring WebFluxilla toteutettu REST-api eroaa edellisessä tehtävässä toteutetusta REST-apista?
 * ---------------------------------------------------------------------------------------------------
 * 
 * Struktuaalisesti kummatkin REST-API:t muistuttavat toisiaan tiedostoineen. Suurin ero sovelluksissa
 * on niiden palautusmalli - aiempi REST-API palauttaa olion tai ResponseEntityn, kun taas WebFlux
 * REST-API palauttaa Flux tai Mono -arvon. WebFluxin REST-API toimii reaktiivisesti ja palauttaa arvot asynkronisesti, 
 * kun taas edellisessä tehtävässä oleva REST-API ei toimi reaktiivisesti ja palauttaa arvot synkronisesti.
 * Aiempi REST-API käytti normaalia MySQL-instanssia (JDBC) tietokantaan yhdistämisessä, kun taas WebFlux REST-API
 * käyttää pilvessä olevaa MongoDB:tä sekä ajureita, jotka mahdollista tietojen haun MongoDB -tietokannasta asynkronisesti.
 * Model-paketissa ei luoda tällä kertaa JPA-luokkaa, joka käyttää yleensä SQL-palvelimia (MySQL, SQL Server, PostgreSQL),
 * vaan MongoDB:stä tuttu dokumentti, joka annotoidaan MongoDB:n dokumentiksi @Document -annotaatiolla. 
 * 
 */

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebFluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebFluxApplication.class, args);
	}

}
