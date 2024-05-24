package com.review.ReviewMicroservice.Controller;
import com.review.ReviewMicroservice.Dto.ReviewRequestDto;
import com.review.ReviewMicroservice.Dto.ReviewResponseDto;
import com.review.ReviewMicroservice.Exception.ReviewNotFoundException;
import com.review.ReviewMicroservice.Service.ReviewService;
import jakarta.ws.rs.NotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    private final ReviewService reviewService;



    @GetMapping("/")
    public ResponseEntity<?> getAllReviews() {
        try {
            List<ReviewResponseDto> reviews = reviewService.getAllReviews();
            return new ResponseEntity<>(reviews, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch reviews", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReviewById(@PathVariable Long id) {
        try {
            ReviewResponseDto review = reviewService.getReviewById(id);
            return new ResponseEntity<>(review, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to fetch review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> addReview(@RequestBody ReviewRequestDto reviewDto) {
        try {
            ReviewResponseDto responseDto = reviewService.addReview(reviewDto);
            return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
        } catch (Exception e) {
            String errorMessage = "Failed to create review. Error: " + e.getMessage();
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateReview(@PathVariable Long id, @RequestBody ReviewRequestDto reviewRequest) {
        try {
            ReviewResponseDto updatedReview = reviewService.updateReview(id, reviewRequest);
            return new ResponseEntity<>(updatedReview, HttpStatus.OK);
        } catch (ReviewNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable Long id) {
        try {
            reviewService.deleteReview(id);
            return ResponseEntity.ok("Review with ID " + id + " deleted successfully");
        } catch (NotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to delete review", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/product/{productId}")
    public List<ReviewResponseDto> getReviewResponsesByProductId(@PathVariable Long productId) {
        return reviewService.getReviewResponsesByProductId(productId);
    }


}
