package com.review.ReviewMicroservice.Service;
import com.review.ReviewMicroservice.Component.ProductExistenceClient;
import com.review.ReviewMicroservice.Component.UserExistenceClient;
import com.review.ReviewMicroservice.Dto.ReviewRequestDto;
import com.review.ReviewMicroservice.Dto.ReviewResponseDto;
import com.review.ReviewMicroservice.Entity.Review;
import com.review.ReviewMicroservice.Exception.ReviewNotFoundException;
import com.review.ReviewMicroservice.Repository.ReviewRepository;
import com.review.ReviewMicroservice.Util.MappingProfile;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductExistenceClient productExistenceClient;
    private final UserExistenceClient userExistenceClient;

    @Override
    public List<ReviewResponseDto> getAllReviews() {
        List<Review> reviews = reviewRepository.findAll();

        return reviews.stream()
                .map(review -> {
                    ReviewResponseDto reviewResponseDto = MappingProfile.mapToReviewDto(review);
                    return reviewResponseDto;
                })
                .collect(Collectors.toList());
    }


    @Override
    public ReviewResponseDto addReview(ReviewRequestDto reviewDto) {
        //if(productExistenceClient.checkProductExistence(reviewDto.getProductId())==null) throw new ReviewNotFoundException("Product not found with Id: " + reviewDto.getProductId());
        if(!(Boolean)productExistenceClient.checkProductExistence(reviewDto.getProductId())) throw new ReviewNotFoundException("Product not found with ID: " + reviewDto.getProductId());
        //if(!(Boolean)userExistenceClient.checkUserExistence(reviewDto.getCustomerId())) throw new ReviewNotFoundException("Custumer not found with ID: " + reviewDto.getProductId());
        var review = MappingProfile.mapToReviewEntity(reviewDto);
        return MappingProfile.mapToReviewDto(reviewRepository.save(review));
    }

    @Override
    public ReviewResponseDto getReviewById(Long id) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        ReviewResponseDto reviewResponseDto = MappingProfile.mapToReviewDto(review);
        return reviewResponseDto;
    }
    @Override
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewRequest) throws ReviewNotFoundException {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found with id: " + id));

        review.setComment(reviewRequest.getComment());
        review.setRating(reviewRequest.getRating());
        review.setCustomerId(reviewRequest.getCustomerId());
        review.setProductId(reviewRequest.getProductId());
        Review updatedReview = reviewRepository.save(review);
        return MappingProfile.mapToReviewDto(updatedReview);
    }


    @Override

    public void deleteReview(Long id) {
        if (reviewRepository.existsById(id)) {
            reviewRepository.deleteById(id);
        } else {
            throw new ReviewNotFoundException("Review not found with id: " + id);
        }
    }
    @Override
    public List<ReviewResponseDto> getReviewResponsesByProductId(Long productId) {
        List<Review> reviews = reviewRepository.findByProductId(productId);
        return reviews.stream()
                .map(MappingProfile::mapToReviewDto)
                .collect(Collectors.toList());
    }
    //getReviewbyratingandproductId()
}
