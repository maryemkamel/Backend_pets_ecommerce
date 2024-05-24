package com.catalog.CatalogMicroservice.Config;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            // Ajouter les en-têtes d'authentification nécessaires
            requestTemplate.header("Authorization", "Bearer <votre_token>");
        };
    }
}