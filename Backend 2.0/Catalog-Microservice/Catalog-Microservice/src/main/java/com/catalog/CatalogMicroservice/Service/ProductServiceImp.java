package com.catalog.CatalogMicroservice.Service;

import com.catalog.CatalogMicroservice.Client.ClientFeignClient;
import com.catalog.CatalogMicroservice.Client.ReviewServiceClient;
import com.catalog.CatalogMicroservice.Dto.*;
import com.catalog.CatalogMicroservice.Entity.Category;
import com.catalog.CatalogMicroservice.Entity.Product;
import com.catalog.CatalogMicroservice.Entity.SubCategory;
import com.catalog.CatalogMicroservice.Exception.CategoryNotFoundException;
import com.catalog.CatalogMicroservice.Exception.SubCategoryNotFoundException;
import com.catalog.CatalogMicroservice.Repository.CategoryRepo;
import com.catalog.CatalogMicroservice.Repository.ProductRepo;
import com.catalog.CatalogMicroservice.Repository.SubCategoryRepo;
import com.catalog.CatalogMicroservice.Util.MappingProfile;

import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ProductServiceImp implements ProductService{

    private final ProductRepo productRepository;
    private final ReviewServiceClient reviewServiceClient;

    private final CategoryRepo categoryRepository;
    private final SubCategoryRepo sousCategoryRepository;
    private final ClientFeignClient clientFeignClient;
   // @Value("${your.auth.token}")

    private final String authToken="eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtYXJ5ZW1rYW1lbC4xOUBnbWFpbC5jb20iLCJpc3MiOiJbUm9sZURUTyhpZD0xLCByb2xlPUFETUlOKV0iLCJpYXQiOjE3MTQxMzE2ODUsImV4cCI6MTcxNDEzNTI4NX0.pTqmd16vEPIXtoPpbnTy8vI6rLJPC0BS15EQtVXgKrk";

    @Override
    public ProductReviewDto getProductWhitReviewById(Long id) throws Exception {
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        List<ReviewDto> reviews = reviewServiceClient.getReviewsByProductId(product.getId());
        for (ReviewDto review : reviews) {
            // Inclure le jeton d'authentification en tant qu'en-tête "Authorization"
            ClientDto clientDto = clientFeignClient.getClientById("Bearer " + authToken, review.getCustomerId());
            //clientDto.setUserId(clientDto.getUserId());
           // System.out.println("client id " + clientDto.getClientid());
           // clientDto.setFullname(clientDto.getFirstname() + "" + clientDto.getLastname());
            review.setClientDto(clientDto);

        }
        ProductReviewDto productResponseDto = MappingProfile.mapToProductReviewDto(product, reviews);
        return productResponseDto;
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {
        return productRepository.findAll().stream()
                .map(MappingProfile::mapToProductDto)
                .collect(Collectors.toList());
    }

    @Override
    public ProductResponseDto createProduct(ProductRequestDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        SubCategory subCategory = sousCategoryRepository.findById(productDto.getSubCategoryId())
                .orElseThrow(() -> new SubCategoryNotFoundException("SubCategory not found"));
        Product product = MappingProfile.mapToProductEntity(productDto);
        product.setCategory(category);
        product.setSubCategory(subCategory);
        Product savedProduct = productRepository.save(product);
        return MappingProfile.mapToProductDto(savedProduct);
    }


   @Override
    public ProductResponseDto getProductById(Long id) throws Exception {
            var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("product not found"));
            return MappingProfile.mapToProductDto(product);

    }


    @Override
    public ProductResponseDto updateProduct(Long id, ProductRequestDto productDto) throws Exception {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Product not found"));
        existingProduct.setName(productDto.getName());
        existingProduct.setShortDescription(productDto.getShortDescription());
        existingProduct.setLongDescription(productDto.getLongDescription());
        existingProduct.setImage(productDto.getImage());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setStockQuantity(productDto.getStockQuantity());
        existingProduct.setActive(productDto.isActive());
        Product updatedProduct = productRepository.save(existingProduct);
        return MappingProfile.mapToProductDto(updatedProduct);
    }


    @Override
    public void deleteProduct(Long id) throws Exception {
        var product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found"));
        productRepository.delete(product);
    }

    @Override
    public ProductResponseDto getProductByName(String productName) throws Exception {
        Product product = productRepository.findByName(productName)
                .orElseThrow(() -> new NotFoundException("Product not found with name: " + productName));
        return MappingProfile.mapToProductDto(product);
    }

    @Override
    public List<ProductResponseDto> searchProductsByName(String ProductName) {
        List<ProductResponseDto> products = productRepository.findByKeyword(ProductName);
        return products;
    }

    @Override
    public Page<ProductResponseDto> findProductsWithPagination(int offset, int pageSize){
        Page<Product> products = productRepository.findAll(PageRequest.of(offset, pageSize));
        return  products.map(MappingProfile::mapToProductDto);
    }
    @Override
    public List<ProductResponseDto> getProductsBySubCategory(Long subCategoryId){
        if (!sousCategoryRepository.existsById(subCategoryId)) {
            throw new SubCategoryNotFoundException("SubcategoryID " + subCategoryId +" not found: " );
        }
        List<Product> products = productRepository.findProductsBySubCategoryId(subCategoryId);
        List<ProductResponseDto> productsDtos = products.stream()
                .map(MappingProfile::mapToProductDto)
                .collect(Collectors.toList());

        return productsDtos;
    }

}
