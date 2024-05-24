package com.catalog.CatalogMicroservice.Dto;

import com.catalog.CatalogMicroservice.Entity.Category;
import com.catalog.CatalogMicroservice.Entity.SubCategory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private Long id;

    private String name;
    private String shortDescription;
    private String longDescription;
    private String image;
    private BigDecimal price;
    private Long categoryId;
    private Long subCategoryId;
    private int stockQuantity;
    private boolean isActive;


}
