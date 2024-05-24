package com.review.ReviewMicroservice.Util;

import com.review.ReviewMicroservice.Dto.ReviewRequestDto;
import com.review.ReviewMicroservice.Dto.ReviewResponseDto;
import com.review.ReviewMicroservice.Entity.Review;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public class MappingProfile {
    private static final ModelMapper modelMapper = new ModelMapper();
    static {
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

        modelMapper.addMappings(new PropertyMap<ReviewRequestDto, Review>() {
            @Override
            protected void configure() {
                map().setProductId(source.getProductId());
                map().setCustomerId(source.getCustomerId());
            }
        });
    }
    public static ReviewResponseDto mapToReviewDto(Review review) {
        return modelMapper.map(review, ReviewResponseDto.class);
    }

    public static Review mapToReviewEntity(ReviewRequestDto reviewRequest) {
        return modelMapper.map(reviewRequest, Review.class);
    }
}
