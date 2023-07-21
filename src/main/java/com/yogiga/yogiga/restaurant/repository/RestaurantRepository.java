package com.yogiga.yogiga.restaurant.repository;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Page<Restaurant> findAllByOrderByIdDesc(Pageable pageable);

    Restaurant findByAddress(String address);

}
