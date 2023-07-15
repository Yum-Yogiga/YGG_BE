package com.yogiga.yogiga.restaurant.service;

import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public RestaurantResponseDto getResById(Long restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);
        return RestaurantResponseDto.toDto(restaurant);
    }

    @Override
    public Page<RestaurantResponseDto> getAllRes(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByOrderByIdDesc(pageable);
        return restaurantPage.map(RestaurantResponseDto::toDto);
    }

    @Override
    public Long createRestaurant(RestaurantDto restaurantDto) {
        User user = SecurityUtil.getUser();
        Restaurant restaurant = Restaurant.toEntity(restaurantDto);

        restaurantRepository.save(restaurant);
        return restaurant.getId();
    }

    @Override
    public Long updateRestaurant(Long restaurantId, RestaurantDto restaurantDto) {
        User user = SecurityUtil.getUser();
        Restaurant restaurant = findRestaurant(restaurantId);

        restaurant.update(restaurantDto);
        return restaurant.getId();
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Transactional(readOnly = true)
    protected Restaurant findRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new CustomException(ErrorCode.RESTAURANT_NOT_FOUND_ERROR, "해당 식당이 존재하지 않습니다. ");
        }
        return restaurant.get();
    }


}
