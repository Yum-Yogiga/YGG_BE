package com.yogiga.yogiga.review.service;

import com.yogiga.yogiga.review.dto.ReviewDto;
import com.yogiga.yogiga.review.dto.ReviewResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewService {

    ReviewResponseDto getReviewById(Long reviewId);

    Page<ReviewResponseDto> getAllReviewByRestaurantId(Long restaurantId, Pageable pageable);

    Long createReview(Long restaurantId, ReviewDto reviewDto);

    Long updateReview(Long reviewId, ReviewDto reviewDto);

    void deleteReview(Long reviewId);
}
