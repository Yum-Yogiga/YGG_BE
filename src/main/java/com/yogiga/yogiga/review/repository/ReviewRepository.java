package com.yogiga.yogiga.review.repository;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByRestaurantOrderByCreateDateDesc(Restaurant restaurant, Pageable pageable);
}
