package com.springmvc.currencyconversion.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.springmvc.currencyconversion.model.CurrencyConversionBean;
import com.springmvc.currencyconversion.proxy.CurrencyExchangeServiceProxy;

/**
 * Normaali REST-kontrolleri, joka toteuttaa muutaman
 * GET-funktion. 
 */
@RestController
public class CurrencyConversionController {
  
  // Automaattisesti injektoi proxy-palvelu
  @Autowired
  private CurrencyExchangeServiceProxy proxy;
  // Haluttujen tietojen loggaus konsoliin
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  /**
   * Normaali tapa, jolla voidaan olla yhteydessä Forex-palveluun. Kun tätä funktiota
   * kutsutaan, se kutsuu Forex-palvelun GET-kyselyä, mikä tuottaa valuutanvaihtoarvon.
   * Kun arvo sekä portti palautuu. Se luo vastauksen käyttämällä CurrencyConversinBean 
   * -beania, jonka se palauttaa.
   * 
   * @param from - Mistä valuutasta käännetään
   * @param to - Mihin valuuttaan käännetän
   * @param quantity - Rahamäärä, mikä käännetään
   * @return - Vastaus mikä kertoo kaikki metatiedot kyselystä, sekä valuutanvaihtoarvon sekä määrän
   */
  @GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to,
      @PathVariable BigDecimal quantity) {

    Map<String, String> uriVariables = new HashMap<>();
    uriVariables.put("from", from);
    uriVariables.put("to", to);

    ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(
        "http://localhost:8000/currency-exchange/from/{from}/to/{to}", CurrencyConversionBean.class,
        uriVariables);

    CurrencyConversionBean response = responseEntity.getBody();

    return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
        quantity.multiply(response.getConversionMultiple()), response.getPort());
  }
  
  
  /**
   * Feignin tapa tehdä REST-kysely ylemmän sijaan. Se kutsuu repositoryä, joka tekee Feignin avulla REST-kyselyn
   * Forex-mikropalveluun. Palautetut tiedot laitetaan väliaikaisesti CurrencyConversionBean -objektiin. Lopuksi
   * luomme uuden bean-objektin, joka palautetaan käyttäjälle.
   * 
   * @param from - Mistä valuutasta käännetään
   * @param to - Mihin valuuttaan käännetän
   * @param quantity - Rahamäärä, mikä käännetään
   * @return - Vastaus mikä kertoo kaikki metatiedot kyselystä, sekä valuutanvaihtoarvon sekä määrän
   */
  @GetMapping("/currency-converter-feign/from/{from}/to/{to}/quantity/{quantity}")
  public CurrencyConversionBean convertCurrencyFeign(@PathVariable String from, @PathVariable String to,
      @PathVariable BigDecimal quantity) {

    CurrencyConversionBean response = proxy.retrieveExchangeValue(from, to);

    logger.info("{}", response);

    return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity,
        quantity.multiply(response.getConversionMultiple()), response.getPort());
  }
  
}
