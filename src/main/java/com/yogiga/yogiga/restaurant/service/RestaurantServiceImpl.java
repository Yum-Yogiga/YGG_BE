package com.yogiga.yogiga.restaurant.service;

import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.restaurant.dto.MenuDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import com.yogiga.yogiga.restaurant.entity.Menu;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.repository.MenuRepository;
import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final MenuRepository menuRepository;

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
        List<MenuDto> menuDtoList = restaurantDto.getMenuDtoList();

        List<Menu> menuList = menuDtoList.stream()
                .map(menuDto -> {
                    Menu menu = Menu.toEntity(menuDto);
                    menu.setRestaurant(restaurant); // 식당과의 연관관계 설정
                    return menu;
                }).toList();
        restaurantRepository.save(restaurant);
        menuRepository.saveAll(menuList);

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
