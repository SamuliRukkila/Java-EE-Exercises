package com.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point Spring Boot Java-class which enables one to start 
 * the Rest API -application for requests. @SpringBootApplication -annotation
 * triggers auto-configuration and component-scanning automatically.
 * 
 * @author samuli
 */
@SpringBootApplication
public class SpringRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestApiApplication.class, args);
	}

}
