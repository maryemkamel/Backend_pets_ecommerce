package com.catalog.CatalogMicroservice.Service;
import com.catalog.CatalogMicroservice.Entity.Category;
import com.catalog.CatalogMicroservice.Dto.SubCategoryRequestDto;
import com.catalog.CatalogMicroservice.Dto.SubCategoryResponseDto;
import com.catalog.CatalogMicroservice.Entity.SubCategory;
import com.catalog.CatalogMicroservice.Exception.CategoryNotFoundException;
import com.catalog.CatalogMicroservice.Exception.SubCategoryAlreadyExistsException;
import com.catalog.CatalogMicroservice.Repository.CategoryRepo;
import com.catalog.CatalogMicroservice.Repository.ProductRepo;
import com.catalog.CatalogMicroservice.Repository.SubCategoryRepo;
import com.catalog.CatalogMicroservice.Util.MappingProfile;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubCategoryServiceImp implements SubCategoryService {

//f category khasni njbed list dial subcategory ou f subcategory njib list dial product
    private final SubCategoryRepo sousCategoryRepository;
    private final CategoryRepo CategoryRepository;
    private final ProductRepo productRepository;
    public boolean subCategoryExists(String name) {
        return sousCategoryRepository.findByName(name) != null;
    }
    public boolean CategoryIdExists(Long id) {
        return sousCategoryRepository.findSubcategoryByCategoryId(id) != null;
    }


 

    @Override
    public List<SubCategoryResponseDto> getAllSubCategories() {
        return sousCategoryRepository.findAll().stream()
                .map(MappingProfile::mapToSubCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public SubCategoryResponseDto createSubCategory(SubCategoryRequestDto subCategoryDto) {
        String subCategoryName = subCategoryDto.getName();
        Long categoryId= subCategoryDto.getCategoryId();
        if (subCategoryExists(subCategoryName) && !CategoryIdExists(categoryId) ) {
            throw new SubCategoryAlreadyExistsException("SubCategory already exists");
        }
        SubCategory subCategory = MappingProfile.mapToSubCategoryEntity(subCategoryDto);
        Category category = CategoryRepository.findById(subCategoryDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        subCategory.setCategory(category);
        return MappingProfile.mapToSubCategoryDto(sousCategoryRepository.save(subCategory));
    }


    @Override
    public SubCategoryResponseDto getSubCategoryById(Long id) throws Exception {
        var sousCategory = sousCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("SubCategory not found"));
        return MappingProfile.mapToSubCategoryDto(sousCategory);    }

    @Override
    public SubCategoryResponseDto updateSubCategory(Long id, SubCategoryRequestDto SousCategoryDto) throws Exception {
        var sousCategory = sousCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("SubCategory not found"));
        sousCategory.setName(SousCategoryDto.getName());
        return MappingProfile.mapToSubCategoryDto(sousCategoryRepository.save(sousCategory));
    }
    @Override
    public void deleteSubCategory(Long id) throws Exception {
        var souscategory = sousCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("SubCategory not found"));
        /*List<Product> products = souscategory.getProducts();
        if (products != null) {
            for (Product product : products) {
                productRepository.delete(product);
            }
        }
         */
        sousCategoryRepository.delete(souscategory);
    }
    @Override
    public List<SubCategoryResponseDto> getSubCategoriesByCategoryId(Long categoryId){
        if (!CategoryRepository.existsById(categoryId)) {
            throw new CategoryNotFoundException("CategoryID " + categoryId +" not found: " );
        }
        List<SubCategory> subCategories = sousCategoryRepository.findSubcategoryByCategoryId(categoryId);
        List<SubCategoryResponseDto> subCategoryResponseDtos = subCategories.stream()
                .map(MappingProfile::mapToSubCategoryDto)
                .collect(Collectors.toList());
        return subCategoryResponseDtos;
    }


}
