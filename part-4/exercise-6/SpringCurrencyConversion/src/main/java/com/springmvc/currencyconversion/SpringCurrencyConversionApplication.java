package com.springmvc.currencyconversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Valuutanmuutospalvelu, joka voi muuntaa valuutan
 * toiseen valuuttaan. Se käyttää Forex-mikropalvelua
 * saadakseen nykyiset valuutanvaihtoarvot. CCS on 
 * palvelunkäyttäjä.
 * 
 * @EnableFeignClients - Annotaatio, joka etsii rajapintoja,
 * jotka ilmoittavat olevansa Feign-asiakkaita (@FeignClient).
 * Konfiguroi komponentin skannaus -direktiivit käytettäviksi
 * 
 * @EnableDiscoveryClient - Kertoo, että Springin etsintä-
 * palvelulla on monia totetutuksia (esim. Eureka). Kertoo
 * siis, että tämä palvelu tulee käyttämään Eurekaa.
 */ 

@SpringBootApplication
@EnableFeignClients("com.springmvc.currencyconversion.proxy")
@EnableDiscoveryClient
public class SpringCurrencyConversionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCurrencyConversionApplication.class, args);
	}

}
