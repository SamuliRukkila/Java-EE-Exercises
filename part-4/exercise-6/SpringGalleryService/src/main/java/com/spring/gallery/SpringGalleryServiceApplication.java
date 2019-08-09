package com.spring.gallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * REST-client joka kutsuu (ja kuluttaa) muita palveluita
 * (REST API) mikropalvelusovelluksessamme.
 * 
 * Gallery-palvelu kutsuu REST-kyselyissä kuva-palvelua, jolta
 * se saa listan kaikista kuvista.
 */

@SpringBootApplication
@EnableEurekaClient
public class SpringGalleryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringGalleryServiceApplication.class, args);
	}

}

/**
 * Konfiguraatioluokka, joka palauttaa uuden RestTemplaten. Se on
 * objekti, joka pystyy lähettämään pyyntöjä REST API -palveluihin.
 * Tässä esimerkissä tätä RestTemplatea käytetään kutsumaan
 * kuva-palvelua (SpringImageService).
 * 
 * @Configuration - Kertoo, että luokka ilmoittaa yhden tai useamman
 *  (@Bean)-menetelmän ja Spring-container voi käsitellä sen tuottaman
 *  bean-määritelmät ja palvelupyynnöt näille beaneille suorituksen aikana.
 * @Bean - Kertoo, että funktio tuottaa beanin, jota hallitaan Spring-containerissa
 * @LoadBalanced - Annotaatio, jolla merkitään RestTemplate-bean, joka tullaan
 *  konfiguroimaan käyttämään LoadBalancerClienttiä (Ribbon)
 * 
 * @author samuli
 *
 */
@Configuration
class RestTemplateConfig {
  
  @Bean
  @LoadBalanced
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
  
}
