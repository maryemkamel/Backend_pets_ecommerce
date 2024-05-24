package com.catalog.CatalogMicroservice.Client;
import com.catalog.CatalogMicroservice.Dto.ClientDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-microservice")
public interface ClientFeignClient {

    @GetMapping("/users/{id}")
    ClientDto getClientById(@RequestHeader("Authorization") String authorization, @PathVariable("id") Long clientId);
}


