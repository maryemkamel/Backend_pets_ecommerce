package com.catalog.CatalogMicroservice.Dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor@NoArgsConstructor@Data@Builder
public class ClientDto {
    private Long userId;
    private String firstname;
    private String lastname;
   // private String fullname;



}
