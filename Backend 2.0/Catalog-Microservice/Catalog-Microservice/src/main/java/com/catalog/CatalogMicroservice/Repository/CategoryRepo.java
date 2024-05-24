package com.catalog.CatalogMicroservice.Repository;

import com.catalog.CatalogMicroservice.Entity.Category;
import com.catalog.CatalogMicroservice.Entity.Product;
import com.catalog.CatalogMicroservice.Entity.SubCategory;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepo extends JpaRepository<Category,Long> {
 Category findByName(String name);
 @Override
 @EntityGraph(attributePaths = "sousCategories")
 List<Category> findAll();
 @EntityGraph(attributePaths = "sousCategories")
 Category getById(Long id);
}
