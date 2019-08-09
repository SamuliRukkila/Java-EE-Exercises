package com.microservice.forex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Normaali Spring Bootilla tuotettu REST-palvelu. Toimii
 * mikropalveluna palvelun-hakijoille.
 * 
 * SpringMicroServiceForex -applikaatio on palveluntarjoaja. Se
 * tarjoaa valuutanvaihtoarvot eri valuutoille REST:n avulla.
 * 
 */
@SpringBootApplication
public class SpringMicroServiceForexApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringMicroServiceForexApplication.class, args);
	}

}
