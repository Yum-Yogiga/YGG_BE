package com.yogiga.yogiga.review.repository;

import com.yogiga.yogiga.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {

}
