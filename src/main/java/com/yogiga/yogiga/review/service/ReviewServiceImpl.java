package com.yogiga.yogiga.review.service;

import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import com.yogiga.yogiga.review.dto.ReviewDto;
import com.yogiga.yogiga.review.dto.ReviewResponseDto;
import com.yogiga.yogiga.review.entity.Review;
import com.yogiga.yogiga.review.repository.ReviewRepository;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService{

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    @Override
    public ReviewResponseDto getReviewById(Long reviewId) {
        Review review = findReview(reviewId);
        return ReviewResponseDto.toDto(review);
    }

    @Override
    public Page<ReviewResponseDto> getAllReviewByRestaurantId(Long restaurantId, Pageable pageable) {
        Restaurant restaurant = findRestaurant(restaurantId);
        Page<Review> reviewPage = reviewRepository.findAllByRestaurantOrderByCreateDateDesc(restaurant, pageable);
        return reviewPage.map(ReviewResponseDto::toDto);
    }

    @Override
    public Long createReview(Long restaurantId, ReviewDto reviewDto) {
        User user = SecurityUtil.getUser();
        Restaurant restaurant = findRestaurant(restaurantId);

        Review review = Review.toEntity(user, restaurant, reviewDto);
        reviewRepository.save(review);
        return review.getId();
    }

    @Override
    @Transactional
    public Long updateReview(Long reviewId, ReviewDto reviewDto) {
        User user = SecurityUtil.getUser();
        Review review = findReview(reviewId);
        if (!user.getUserId().equals(review.getUser().getUserId())) {
            throw new CustomException(ErrorCode.AUTHENTICATION_ERROR, "리뷰를 작성한 유저와 일치하지 않습니다. ");
        }
        review.update(reviewDto);
        return reviewId;
    }

    @Override
    public void deleteReview(Long reviewId) {
        User user = SecurityUtil.getUser();
        Review review = findReview(reviewId);
        if (!user.getUserId().equals(review.getUser().getUserId())) {
            throw new CustomException(ErrorCode.AUTHENTICATION_ERROR, "리뷰를 작성한 유저와 일치하지 않습니다. ");
        }
        reviewRepository.delete(review);
    }

    @Transactional(readOnly = true)
    protected Restaurant findRestaurant(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND_ERROR, "해당 식당이 존재하지 않습니다. "));
    }

    @Transactional(readOnly = true)
    protected Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND_ERROR, "해당 리뷰가 존재하지 않습니다. "));
    }
}
