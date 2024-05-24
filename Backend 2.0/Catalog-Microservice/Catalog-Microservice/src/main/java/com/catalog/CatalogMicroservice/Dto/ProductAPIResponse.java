package com.catalog.CatalogMicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductAPIResponse<T> {

    int recordCount;
    T response;

}