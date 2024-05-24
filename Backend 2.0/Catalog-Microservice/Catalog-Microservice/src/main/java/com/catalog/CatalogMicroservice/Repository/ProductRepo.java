package com.catalog.CatalogMicroservice.Repository;
import com.catalog.CatalogMicroservice.Dto.ProductResponseDto;
import com.catalog.CatalogMicroservice.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProductRepo  extends JpaRepository<Product,Long> {

    Optional<Product> findByName(String productName);
    /*@Query(value = "SELECT * FROM products WHERE product_name LIKE CONCAT('%', ?1, '%')", nativeQuery = true)
    List<Product> findProductByName(String name);
     */

   /* @Query("SELECT new com.catalog.CatalogMicroservice.Entity.Dto.ProductResponseDto(" +
            "p.id, p.name, p.shortDescription, p.longDescription, p.image, p.price, " +
            "p.category.id, p.subCategory.id, p.stockQuantity, p.isActive) " +
            "FROM Product p WHERE p.name LIKE %:keyword%")
    List<ProductResponseDto> findByKeyword(@Param("keyword") String keyword);

    */
   /*@Query("SELECT new com.catalog.CatalogMicroservice.Dto.ProductResponseDto(" +
           "p.id, p.name, p.shortDescription, p.longDescription, p.image, p.price, " +
           "p.category.id, p.subCategory.id, p.stockQuantity, p.isActive) " +
           "FROM Product p WHERE p.name LIKE %:keyword%")
   List<ProductResponseDto> findByKeyword(@Param("keyword") String keyword);
   //ancienne methode

    */
   @Query("SELECT new com.catalog.CatalogMicroservice.Dto.ProductResponseDto(" +
           "p.id, p.name, p.shortDescription, p.longDescription, p.image, p.price, " +
           "p.category.id, p.subCategory.id, p.stockQuantity, p.isActive) " +
           "FROM Product p WHERE LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
   List<ProductResponseDto> findByKeyword(@Param("keyword") String keyword);

    List<Product> findProductsBySubCategoryId(Long subCategoryId);
}
