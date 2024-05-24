package com.catalog.CatalogMicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CatalogMicroserviceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CatalogMicroserviceApplication.class, args);
	}

}
