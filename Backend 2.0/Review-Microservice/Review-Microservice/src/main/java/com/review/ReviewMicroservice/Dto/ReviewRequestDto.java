package com.review.ReviewMicroservice.Dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDto {
    private Long productId;
    private Long customerId;
    private String comment;
    private int rating;

}
