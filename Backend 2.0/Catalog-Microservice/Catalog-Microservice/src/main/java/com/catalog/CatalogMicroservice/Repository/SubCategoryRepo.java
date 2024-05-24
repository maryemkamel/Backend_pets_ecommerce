package com.catalog.CatalogMicroservice.Repository;

import com.catalog.CatalogMicroservice.Entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubCategoryRepo extends JpaRepository<SubCategory,Long> {
    SubCategory findByName(String name);
    List<SubCategory> findSubcategoryByCategoryId(Long categoryId);

}
