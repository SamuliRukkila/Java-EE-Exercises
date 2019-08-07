package com.springmvc.currencyconversion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.springmvc.currencyconversion.proxy")
@EnableDiscoveryClient
public class SpringCurrencyConversionApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCurrencyConversionApplication.class, args);
	}

}
