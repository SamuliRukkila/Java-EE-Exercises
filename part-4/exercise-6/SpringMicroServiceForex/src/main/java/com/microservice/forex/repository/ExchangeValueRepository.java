package com.microservice.forex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.forex.model.ExchangeValue;

/**
 * Normaali rajapinta-repository, joka laajentaa itseään Springin
 * JPARepository -rajapinnalla.
 */
public interface ExchangeValueRepository extends JpaRepository<ExchangeValue, Long> {
  
  /**
   * Haluamme kyselyn, joka tekee muuntoarvon valuutasta toiseen.
   * Määrittelemme sille oman kyselymenetelmän.
   */
  ExchangeValue findByFromAndTo(String from, String to);
  
}
