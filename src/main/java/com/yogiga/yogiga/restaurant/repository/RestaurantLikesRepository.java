package com.yogiga.yogiga.restaurant.repository;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.entity.RestaurantLikes;
import com.yogiga.yogiga.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantLikesRepository extends JpaRepository<RestaurantLikes, Long> {
    boolean existsByUserAndRestaurant(User user, Restaurant restaurant);
}
