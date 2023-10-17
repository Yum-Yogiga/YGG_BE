package com.yogiga.yogiga.review.service;

import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import com.yogiga.yogiga.review.dto.ReviewDto;
import com.yogiga.yogiga.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Long createReview(Long restaurantId, ReviewDto reviewDto) {
        return null;
    }

    @Override
    public Long updateReview(Long restaurantId, ReviewDto reviewDto) {
        return null;
    }

    @Override
    public void deleteReview(Long reviewId) {

    }
}
