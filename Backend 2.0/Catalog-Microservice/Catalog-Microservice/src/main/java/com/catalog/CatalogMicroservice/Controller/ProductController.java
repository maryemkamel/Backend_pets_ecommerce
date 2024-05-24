package com.catalog.CatalogMicroservice.Controller;
import com.catalog.CatalogMicroservice.Client.ClientFeignClient;
import com.catalog.CatalogMicroservice.Dto.*;
import com.catalog.CatalogMicroservice.Exception.CategoryNotFoundException;
import com.catalog.CatalogMicroservice.Exception.SubCategoryNotFoundException;
import com.catalog.CatalogMicroservice.Service.ProductService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/products")

/*@CrossOrigin(o<rigins = "*", allowedHeaders = "*")

 */
public class ProductController {

    private final ProductService productService;
    private final ClientFeignClient clientFeignClient;


    @GetMapping("/test-client/{id}")
    public String testClient(@PathVariable Long id) {
        // Simuler l'autorisation en utilisant un jeton factice
        String authToken = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJlbWFpbG1pY3Jvc2VydmljZTE5OTRAZ21haWwuY29tIiwiaXNzIjoiW1JvbGVEVE8oaWQ9MSwgcm9sZT1BRE1JTildIiwiaWF0IjoxNzE0MDAxNDAxLCJleHAiOjE3MTQwMDUwMDF9.enGd4ZmpahsKrMlwuaWw3si1rPZ7U1glZzPrsq48-ew";

        // Appel à getClientById avec l'ID du client et le jeton d'autorisation
        ClientDto clientDto = clientFeignClient.getClientById("Bearer " + authToken, id);

        if (clientDto != null) {
            // Récupérer et afficher l'ID du client
            Long clientId = clientDto.getUserId();
            return "ID du client : " + clientId;
        } else {
            return "Client non trouvé";
        }
    }

    @GetMapping("/{productId}/reviews")
    public ResponseEntity<?> getProductWithReviewById(@PathVariable Long productId) {
        try {
            ProductReviewDto productReviewDto = productService.getProductWhitReviewById(productId);
            return new ResponseEntity<>(productReviewDto, HttpStatus.OK);
        } catch (Exception e) {
            String errorMessage = "Failed to retrieve product with reviews. Error message: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/")
    public ResponseEntity<?> getAllProducts() {
        try {
            return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch products", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/create")
    public ResponseEntity<?> createProduct(@RequestBody ProductRequestDto productRequestDto) {
        try {
            ProductResponseDto productResponseDto = productService.createProduct(productRequestDto);
            return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
        } catch (CategoryNotFoundException | SubCategoryNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto productRequestDto) {
        try {
            return new ResponseEntity<>(productService.updateProduct(id, productRequestDto), HttpStatus.OK);
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update product", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Afficher l'erreur exacte renvoyée par le serveur
            String errorMessage = "Failed to delete Product: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/getByName")
    public ResponseEntity<ProductResponseDto> getProductByName(@RequestParam String productName) {
        try {
            ProductResponseDto productDto = productService.getProductByName(productName);
            return ResponseEntity.ok(productDto);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponseDto>> searchProductsByName(@RequestParam String productName) {
        try {
            List<ProductResponseDto> products = productService.searchProductsByName(productName);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/pagination/{offset}/{pageSize}")
    private ProductAPIResponse<Page<ProductResponseDto>> getProductsWithPagination(@PathVariable int offset, @PathVariable int pageSize) {
        Page<ProductResponseDto> productsWithPagination = productService.findProductsWithPagination(offset, pageSize);
        return new ProductAPIResponse<>(productsWithPagination.getSize(), productsWithPagination);
    }
    @GetMapping("/subcategory/{subCategory}")

    public ResponseEntity<?> getProductsBySubCategoryId(Long subCategoryId){
        try{
            List<ProductResponseDto> productResponseDto = productService.getProductsBySubCategory(subCategoryId);
            return ResponseEntity.ok(productResponseDto);
        }catch (SubCategoryNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }

    }
}
