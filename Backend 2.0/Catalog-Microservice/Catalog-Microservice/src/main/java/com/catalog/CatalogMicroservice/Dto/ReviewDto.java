package com.catalog.CatalogMicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long productId;
    private Long customerId;
    private String comment;
    private int rating;
    private ClientDto clientDto;

}