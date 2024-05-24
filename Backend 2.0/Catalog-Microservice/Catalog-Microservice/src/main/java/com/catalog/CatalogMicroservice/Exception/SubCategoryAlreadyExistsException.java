package com.catalog.CatalogMicroservice.Exception;

public class SubCategoryAlreadyExistsException extends RuntimeException {
    public SubCategoryAlreadyExistsException(String message) {
        super(message);
    }
}
