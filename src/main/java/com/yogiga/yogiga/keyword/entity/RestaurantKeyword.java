package com.yogiga.yogiga.keyword.entity;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    private Keyword keyword;

    @Column(nullable = false)
    private double score;

    public void setRestaurantKeyword(Restaurant restaurant, Keyword keyword, double score) {
        this.restaurant = restaurant;
        this.keyword = keyword;
        this.score = score;
    }

    public void updateScore(double score) {
        this.score = score;
    }

    public int getScoreCount() {
        return (int) (score * 100.0);
    }
}
