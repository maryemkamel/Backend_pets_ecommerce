package com.catalog.CatalogMicroservice.Client;

import com.catalog.CatalogMicroservice.Dto.ReviewDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@FeignClient(name = "Review-Microservice")
public interface ReviewServiceClient {
    @GetMapping("/reviews/product/{productId}")
    List<ReviewDto> getReviewsByProductId(@RequestParam("productId") Long productId);

}
