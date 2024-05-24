package com.catalog.CatalogMicroservice.Exception;

public class SubCategoryNotFoundException extends RuntimeException {
    public SubCategoryNotFoundException(String message) {
        super(message);
    }
}
