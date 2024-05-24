package com.catalog.CatalogMicroservice.Service;

import com.catalog.CatalogMicroservice.Dto.SubCategoryRequestDto;
import com.catalog.CatalogMicroservice.Dto.SubCategoryResponseDto;

import java.util.List;

public interface SubCategoryService {
    List<SubCategoryResponseDto> getAllSubCategories();
    SubCategoryResponseDto createSubCategory(SubCategoryRequestDto SousCategoryDto);
    SubCategoryResponseDto getSubCategoryById(Long id) throws Exception;
    SubCategoryResponseDto updateSubCategory(Long id, SubCategoryRequestDto SousCategoryDto) throws Exception;
    void deleteSubCategory(Long id) throws Exception;

    List<SubCategoryResponseDto> getSubCategoriesByCategoryId(Long categoryId);
}
