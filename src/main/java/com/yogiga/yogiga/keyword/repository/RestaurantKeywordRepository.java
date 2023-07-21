package com.yogiga.yogiga.keyword.repository;

import com.yogiga.yogiga.keyword.entity.Keyword;
import com.yogiga.yogiga.keyword.entity.RestaurantKeyword;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantKeywordRepository extends JpaRepository<RestaurantKeyword, Long> {

    RestaurantKeyword findByRestaurantAndKeyword(Restaurant restaurant, Keyword keyword);
}
