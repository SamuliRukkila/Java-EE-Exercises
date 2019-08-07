package com.microservice.forex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.forex.model.ExchangeValue;

public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
  
  ExchangeValue findByFromAndTo(String from, String to);
  
}
