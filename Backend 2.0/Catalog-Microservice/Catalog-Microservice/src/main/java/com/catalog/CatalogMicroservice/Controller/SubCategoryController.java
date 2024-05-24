package com.catalog.CatalogMicroservice.Controller;
import com.catalog.CatalogMicroservice.Dto.SubCategoryRequestDto;
import com.catalog.CatalogMicroservice.Dto.SubCategoryResponseDto;
import com.catalog.CatalogMicroservice.Exception.CategoryNotFoundException;
import com.catalog.CatalogMicroservice.Exception.SubCategoryAlreadyExistsException;
import com.catalog.CatalogMicroservice.Service.SubCategoryService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/subcategories")
/*@CrossOrigin(origins = "*", allowedHeaders = "*")

 */

public class SubCategoryController {
    @Autowired
    private final SubCategoryService sousCategoryService;

    @GetMapping("/")
    public ResponseEntity<?> getAllSousCategories() {
        try {
            return new ResponseEntity<>(sousCategoryService.getAllSubCategories(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch subcategories", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSousCategoryById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(sousCategoryService.getSubCategoryById(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch sous-category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubCategory(@RequestBody SubCategoryRequestDto subcategoryRequestDto) {
        try {
            return new ResponseEntity<>(sousCategoryService.createSubCategory(subcategoryRequestDto), HttpStatus.CREATED);
        } catch (SubCategoryAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create subcategory", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateSousCategory(@PathVariable Long id, @RequestBody SubCategoryRequestDto sousCategoryRequestDto) {
        try {
            return new ResponseEntity<>(sousCategoryService.updateSubCategory(id, sousCategoryRequestDto), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update SubCategory", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSousCategory(@PathVariable Long id) {
        try {
            sousCategoryService.deleteSubCategory(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete Sous-Category", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getSubCategoriesByCategoryId(Long categoryId){
        try{
            List<SubCategoryResponseDto> subCategories = sousCategoryService.getSubCategoriesByCategoryId(categoryId);
            return ResponseEntity.ok(subCategories);
        }catch(CategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
