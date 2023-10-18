package com.yogiga.yogiga.review.controller;

import com.yogiga.yogiga.review.dto.ReviewDto;
import com.yogiga.yogiga.review.dto.ReviewResponseDto;
import com.yogiga.yogiga.review.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    @Operation(summary = "특정 리뷰 Id로 조회하기")
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDto> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @Operation(summary = "특정 식당별로 모든 리뷰 조회하기")
    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Page<ReviewResponseDto>> getAllReviewByRestaurantId
            (@PathVariable Long restaurantId,
             @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reviewService.getAllReviewByRestaurantId(restaurantId, pageable));
    }

    @Operation(summary = "특정 식당에 대한 리뷰 작성하기")
    @PostMapping("/restaurants/{restaurantId}")
    public ResponseEntity<Long> createReview(@PathVariable Long restaurantId, @Valid @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.createReview(restaurantId, reviewDto));
    }

    @Operation(summary = "특정 리뷰 업데이트")
    @PutMapping("/{reviewId}")
    public ResponseEntity<Long> updateReview(@PathVariable Long reviewId, @Valid @RequestBody ReviewDto reviewDto) {
        return ResponseEntity.ok(reviewService.updateReview(reviewId, reviewDto));
    }

    @Operation(summary = "특정 리뷰 삭제")
    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable Long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
