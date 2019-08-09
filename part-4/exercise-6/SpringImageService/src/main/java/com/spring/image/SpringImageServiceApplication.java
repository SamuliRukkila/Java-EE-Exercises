package com.spring.image;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Tämä kuva-palvelu (ImageService) toimii kuvien tietolähteenä;
 * jokaisella kuvalla on tunnus, otsikko ja URL.
 * 
 * Se on itsessään toimiva Eureka-client, joka ei tiedä muiden
 * palveluiden olemassaolosta.
 */

@SpringBootApplication
@EnableEurekaClient
public class SpringImageServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringImageServiceApplication.class, args);
	}

}
