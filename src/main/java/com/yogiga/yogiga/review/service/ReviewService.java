package com.yogiga.yogiga.review.service;

import com.yogiga.yogiga.review.dto.ReviewDto;

public interface ReviewService {
    public Long createReview(Long restaurantId, ReviewDto reviewDto);

    public Long updateReview(Long restaurantId, ReviewDto reviewDto);

    public void deleteReview(Long reviewId);
}
