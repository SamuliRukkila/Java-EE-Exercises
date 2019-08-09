package com.eureka.naming.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Projekti, mikä on yksinkertainen Eureka -serveri, joka
 * nitoo yhteen mikropalvelun tuottajat sekä käyttäjät.
 * 
 * application.properties -konfiguraatiotiedostossa annetaan
 * tarvittavat tiedot.
 * 
 * @EnableEurekaServer -annotaatio kertoo, että tässä applikaatiossa
 * aktivoidaan Eureka palvelin
 */
@SpringBootApplication
@EnableEurekaServer
public class SpringEurekaNamingServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringEurekaNamingServerApplication.class, args);
	}

}
