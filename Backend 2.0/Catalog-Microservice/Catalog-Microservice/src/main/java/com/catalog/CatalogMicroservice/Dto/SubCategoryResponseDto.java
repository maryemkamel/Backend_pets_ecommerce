package com.catalog.CatalogMicroservice.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubCategoryResponseDto {

    private Long id;
    private String name;
    private Long categoryId;
    private List<ProductResponseDto> products;


}
