package com.catalog.CatalogMicroservice.Service;


import com.catalog.CatalogMicroservice.Dto.CategoryRequestDto;
import com.catalog.CatalogMicroservice.Dto.CategoryResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryResponseDto> getAllCategories();
    CategoryResponseDto createCategory(CategoryRequestDto CategoryDto);
    CategoryResponseDto getCategoryById(Long id) throws Exception;
    CategoryResponseDto updateCategory(Long id, CategoryRequestDto CategoryDto) throws Exception;
    void deleteCategory(Long id) throws Exception;
}
