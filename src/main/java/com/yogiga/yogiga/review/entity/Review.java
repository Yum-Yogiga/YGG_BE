package com.yogiga.yogiga.review.entity;

import com.yogiga.yogiga.global.entity.BaseTimeEntity;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.review.dto.ReviewDto;
import com.yogiga.yogiga.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    Restaurant restaurant;

    @Column(nullable = false)
    String content;

    @Builder
    public static Review toEntity(ReviewDto reviewDto) {
        return Review.builder()
                .content(reviewDto.getContent())
                .build();
    }

}
