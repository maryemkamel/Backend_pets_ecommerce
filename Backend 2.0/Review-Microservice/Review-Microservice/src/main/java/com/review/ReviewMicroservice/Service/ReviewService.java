package com.review.ReviewMicroservice.Service;

import com.review.ReviewMicroservice.Dto.ReviewRequestDto;
import com.review.ReviewMicroservice.Dto.ReviewResponseDto;
import com.review.ReviewMicroservice.Exception.ReviewNotFoundException;

import java.util.List;

public interface ReviewService {

    ReviewResponseDto addReview(ReviewRequestDto reviewRequest);

    ReviewResponseDto getReviewById(Long id) throws ReviewNotFoundException;

    List<ReviewResponseDto> getAllReviews();

    List<ReviewResponseDto> getReviewResponsesByProductId(Long productId);

    ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewRequest) throws ReviewNotFoundException;

    void deleteReview(Long id);
}
