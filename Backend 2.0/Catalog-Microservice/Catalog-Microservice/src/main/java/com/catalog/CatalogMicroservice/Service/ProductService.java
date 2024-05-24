package com.catalog.CatalogMicroservice.Service;

import com.catalog.CatalogMicroservice.Dto.ProductRequestDto;
import com.catalog.CatalogMicroservice.Dto.ProductResponseDto;
import com.catalog.CatalogMicroservice.Dto.ProductReviewDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<ProductResponseDto> getAllProducts();
    ProductResponseDto createProduct(ProductRequestDto ProductDto);
    ProductResponseDto getProductById(Long id) throws Exception;
    ProductResponseDto updateProduct(Long id, ProductRequestDto ProductDto) throws Exception;
    void deleteProduct(Long id) throws Exception;
    ProductResponseDto getProductByName(String productName) throws Exception;
    List<ProductResponseDto> searchProductsByName(String ProductName);
    Page<ProductResponseDto> findProductsWithPagination(int offset, int pageSize);
    ProductReviewDto getProductWhitReviewById(Long id) throws Exception;
    public List<ProductResponseDto> getProductsBySubCategory(Long subCategoryId);

}
