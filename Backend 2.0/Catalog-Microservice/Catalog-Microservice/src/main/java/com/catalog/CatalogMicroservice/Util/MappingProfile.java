package com.catalog.CatalogMicroservice.Util;


import com.catalog.CatalogMicroservice.Dto.*;
import com.catalog.CatalogMicroservice.Entity.Category;
import com.catalog.CatalogMicroservice.Entity.Product;
import com.catalog.CatalogMicroservice.Entity.SubCategory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();


    /*static {
        // Define explicit mapping for conflicting property setSubCategoryId() in ProductResponseDto
        modelMapper.addMappings(new PropertyMap<Product, ProductResponseDto>() {
            @Override
            protected void configure() {
                // Map Product's SubCategory's Category's CategoryId to ProductResponseDto's SubCategoryId
                map().setSubCategoryId(source.getSubCategory().getCategory().getId());
            }
        });
    }

     */
    public static ProductReviewDto mapToProductReviewDto(Product product, List<ReviewDto> reviews) {
        ProductReviewDto productReviewDto = new ProductReviewDto();
        BeanUtils.copyProperties(product, productReviewDto); // Copier les propriétés de Product vers ProductReviewDto
        productReviewDto.setCategoryId(product.getCategory().getId());
        productReviewDto.setSubCategoryId(product.getSubCategory().getId());
        productReviewDto.setReviews(reviews);
       // productReviewDto.setReviews(re);
      /*  for (ReviewDto review : reviews) {
            // Inclure le jeton d'authentification en tant qu'en-tête "Authorization"
                    review.setCustomerId();
                            setClientid(clientDto.getClientid());
            System.out.println("client id " + clientDto.getClientid());
            clientDto.setFullname(clientDto.getFirstname() + "" + clientDto.getLastname());
            review.setClientDto(clientDto);

        }

       */

        return productReviewDto;
    }


    public static Product mapToProductEntity(ProductRequestDto productRequest){
        return modelMapper.map(productRequest, Product.class);
    }
    public static ProductResponseDto mapToProductDto(Product product){
        return modelMapper.map(product, ProductResponseDto.class);
    }
    public static Category mapToCategoryEntity(CategoryRequestDto categoryRequest){
        return modelMapper.map(categoryRequest, Category.class);
    }
    public static CategoryResponseDto mapToCategoryDto(Category category){
        return modelMapper.map(category, CategoryResponseDto.class);
    }
    public static SubCategory mapToSubCategoryEntity(SubCategoryRequestDto subcategoryDto){
        return modelMapper.map(subcategoryDto, SubCategory.class);
    }
    public static SubCategoryResponseDto mapToSubCategoryDto(SubCategory subcategory){
        return modelMapper.map(subcategory, SubCategoryResponseDto.class);
    }
}