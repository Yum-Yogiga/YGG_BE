package com.yogiga.yogiga.restaurant.repository;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findAllByOrderByIdDesc(Pageable pageable);

    Restaurant findByAddress(String address);

    @Query("SELECT r FROM Restaurant r WHERE r.name = :restaurantName")
    Restaurant findByName(@Param("restaurantName") String restaurantName);
}
