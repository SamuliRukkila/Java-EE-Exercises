package com.microservice.forex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.forex.model.ExchangeValue;
import com.microservice.forex.repository.ExchangeValueRepository;

@RestController
public class ForexController {
  
  @Autowired
  private Environment env;
  
  @Autowired
  private ExchangeValueRepository rep;
  
  
  @GetMapping("/currency-exchange/from/{from}/to/{to}")
  public ExchangeValue retrieveExchangeValue(@PathVariable String from, @PathVariable String to) {
    
    ExchangeValue value = rep.findByFromAndTo(from, to);
    
    value.setPort(Integer.parseInt(env.getProperty("local.server.port")));
    
    return value;
    
  }
  
}
