package com.yogiga.yogiga.restaurant.service;

import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RestaurantService {

    RestaurantResponseDto getResById(Long id);

    Page<RestaurantResponseDto> getAllRes(Pageable pageable);

    Long createRestaurant(RestaurantDto restaurantDto);

    Long updateRestaurant(Long id, RestaurantDto restaurantDto);

    void deleteRestaurant(Long id);
}
