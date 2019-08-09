package com.springmvc.currencyconversion.proxy;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.springmvc.currencyconversion.model.CurrencyConversionBean;

/**
 * Feign tarjoaa paremmin vaihtoehdon kutsuakseen REST-API:a. Kun
 * repositoryn funktiota kutsutaan, se tekee REST-kyselyn Forexin
 * REST-palveluun.
 * 
 * @FeignClient() -annotaatio kertoo, että tämä on Feign client.
 * Url -parametri kertoo, mitä Forex-palvelua on saatavilla
 * 
 * @RibbonClient() -annotaatio kertoo, että tässä rajapinnassa
 * käytetään kuorman jakamiseen tarkoitettua kirjastoa, Ribbonia
 */

@FeignClient(name = "forex-service")
@RibbonClient(name = "forex-service")
public interface CurrencyExchangeServiceProxy {
  
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public CurrencyConversionBean retrieveExchangeValue(
      @PathVariable("from") String from, @PathVariable("to") String to);

}
