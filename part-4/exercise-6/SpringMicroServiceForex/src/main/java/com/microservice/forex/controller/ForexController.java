package com.microservice.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.forex.model.ExchangeValue;
import com.microservice.forex.repository.ExchangeValueRepository;

/**
 * REST-kontrolleri. Kertoo olevansa kontrolleri Springille 
 * skannausvaiheessa annotaatiolla @RestController.
 */

@RestController
public class ForexController {
  
  
  /**
   * Automaattisesti injektoidaan tarvittavat rajapinnat
   * tähän kontrolleriin @Autowired -annotation ansiosta
   */
  
  // Tässä käytämme Springin omaa rajapintaa Environment, jonka
  // avulla saamme helposti tiedon missä portissa instanssi on
  @Autowired
  private Environment env;
  
  @Autowired
  private ExchangeValueRepository rep;
  
  
  /**
   * GET-tyyppinen funktio, joka hakee valuutanmuutos-arvon 
   * "from-" ja "to" -parametreilla käyttäen hyväksi valmista
   * repositorya. Kun arvo on haettu, haetaan Environment -rajapinnan
   * avulla kyseinen mikropalvelun instanssin portti. Palautetaan
   * objekti takaisin sitä kutsuvalle palvelulle (SpringCurrencyConversion).
   * 
   * @param from - Alkuperäinen valuutta
   * @param to - Valuutta, johon halutaan muuttaa
   * @return ExchangeValue -objektin, joka sisältää valuutanmuutosarvon 
   *  sekä instanssin portin
   */
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
    
    ExchangeValue value = rep.findByFromAndTo(from, to);
    
    value.setPort(Integer.parseInt(env.getProperty("local.server.port")));
    
    return value;
    
  }
  
}
