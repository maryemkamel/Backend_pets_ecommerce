package com.catalog.CatalogMicroservice.Service;
import com.catalog.CatalogMicroservice.Entity.Category;
import com.catalog.CatalogMicroservice.Dto.CategoryRequestDto;
import com.catalog.CatalogMicroservice.Dto.CategoryResponseDto;
import com.catalog.CatalogMicroservice.Dto.SubCategoryResponseDto;
import com.catalog.CatalogMicroservice.Entity.SubCategory;
import com.catalog.CatalogMicroservice.Exception.CategoryAlreadyExistsException;
import com.catalog.CatalogMicroservice.Repository.CategoryRepo;
import com.catalog.CatalogMicroservice.Repository.ProductRepo;
import com.catalog.CatalogMicroservice.Repository.SubCategoryRepo;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import com.catalog.CatalogMicroservice.Util.MappingProfile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImp implements CategoryService{
    private final CategoryRepo categoryRepository;
    private final SubCategoryRepo subcategoryRepository;
    private final ProductRepo productRepository;
   /* @Override
    public List<CategoryResponseDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(MappingProfile::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    */

    @Override
    public List<CategoryResponseDto> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponseDtos = new ArrayList<>();

        for (Category category : categories) {
            CategoryResponseDto categoryResponseDto = MappingProfile.mapToCategoryDto(category);
            List<SubCategoryResponseDto> subCategoryResponseDtos = new ArrayList<>();

            // Vérifier si la catégorie a des sous-catégories associées
            if (category.getSousCategories() != null && !category.getSousCategories().isEmpty()) {
                for (SubCategory subCategory : category.getSousCategories()) {
                    SubCategoryResponseDto subCategoryResponseDto = MappingProfile.mapToSubCategoryDto(subCategory);
                    subCategoryResponseDtos.add(subCategoryResponseDto);
                }
            }

            // Définir les sous-catégories dans la réponse de la catégorie
            categoryResponseDto.setSubCategories(subCategoryResponseDtos);
            categoryResponseDtos.add(categoryResponseDto);
        }

        return categoryResponseDtos;
    }

    public boolean categoryExists(String categoryName) {
        return categoryRepository.findByName(categoryName) != null;
    }
    @Override
    public CategoryResponseDto createCategory(CategoryRequestDto CategoryDto) {
        String categoryName = CategoryDto.getName();
        if (categoryExists(categoryName)) {
            throw new CategoryAlreadyExistsException("Category already exist");
        }
        var category = MappingProfile.mapToCategoryEntity(CategoryDto);
      return MappingProfile.mapToCategoryDto(categoryRepository.save(category));

    }

    /*@Override
    public CategoryResponseDto getCategoryById(Long id) throws Exception {
        var category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("category not found"));
        return MappingProfile.mapToCategoryDto(category);
    }

     */
    @Override
    public CategoryResponseDto getCategoryById(Long id) throws NotFoundException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Category not found"));

        List<SubCategory> subCategories = category.getSousCategories();
        List<SubCategoryResponseDto> subCategoryResponseDtos = new ArrayList<>();

        if (subCategories != null && !subCategories.isEmpty()) {
            for (SubCategory subCategory : subCategories) {
                SubCategoryResponseDto subCategoryResponseDto = MappingProfile.mapToSubCategoryDto(subCategory);
                subCategoryResponseDtos.add(subCategoryResponseDto);
            }
        }

        CategoryResponseDto categoryResponseDto = MappingProfile.mapToCategoryDto(category);
        categoryResponseDto.setSubCategories(subCategoryResponseDtos);

        return categoryResponseDto;
    }


    @Override
    public CategoryResponseDto updateCategory(Long id, CategoryRequestDto CategoryDto) throws Exception {
        var category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        category.setName(CategoryDto.getName());
        return MappingProfile.mapToCategoryDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategory(Long id) throws Exception {
        var category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("Category not found"));
        categoryRepository.delete(category);
    }


}
