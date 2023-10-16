package com.yogiga.yogiga.restaurant.entity;

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
public class RestaurantLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    @Builder
    public static RestaurantLikes toEntity(User user, Restaurant restaurant) {
        return RestaurantLikes.builder()
                .user(user)
                .restaurant(restaurant)
                .build();
    }

}
