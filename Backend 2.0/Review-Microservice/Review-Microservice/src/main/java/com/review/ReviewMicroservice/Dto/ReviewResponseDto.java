package com.review.ReviewMicroservice.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ReviewResponseDto {

        private Long id;
        private Long productId;

        private Long customerId;
        private String comment;

        private int rating;
        private Date createdAt;
    }


