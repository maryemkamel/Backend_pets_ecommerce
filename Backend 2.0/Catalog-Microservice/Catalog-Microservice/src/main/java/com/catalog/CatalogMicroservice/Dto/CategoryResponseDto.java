package com.catalog.CatalogMicroservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private Long id;
    private String name;
   // private List<ProductResponseDto> products;
    private List<SubCategoryResponseDto> subCategories;

}
