package com.yogiga.yogiga.restaurant.service;

import com.yogiga.yogiga.global.config.WebClientUtil;
import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.restaurant.dto.MenuDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantResponseDto;
import com.yogiga.yogiga.restaurant.entity.Menu;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.entity.RestaurantLikes;
import com.yogiga.yogiga.restaurant.repository.MenuRepository;
import com.yogiga.yogiga.restaurant.repository.RestaurantLikesRepository;
import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantLikesRepository restaurantLikesRepository;
    private final MenuRepository menuRepository;
    @Value("${ai.api.url}")
    private String aiApiUrl;


    @Override
    @Transactional(readOnly = true)
    public RestaurantResponseDto getResById(Long restaurantId) {
        Restaurant restaurant = findRestaurant(restaurantId);
        return RestaurantResponseDto.toDto(restaurant);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RestaurantResponseDto> getAllRes(Pageable pageable) {
        Page<Restaurant> restaurantPage = restaurantRepository.findAllByOrderByIdDesc(pageable);
        return restaurantPage.map(RestaurantResponseDto::toDto);
    }

    @Override
    public Mono<List<String>> recommendRestaurants(List<Integer> keywordInput) {

        WebClient webClient = WebClientUtil.getBaseUrl(aiApiUrl);
        log.info("recommendRestaurants : {}", aiApiUrl);
        return webClient.post()
                .uri("/model/k_means")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(Map.of("keyword", keywordInput))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<String>>() {});
    }

    @Override
    @Transactional
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
    @Transactional
    public Long updateRestaurant(Long restaurantId, RestaurantDto restaurantDto) {
        User user = SecurityUtil.getUser();
        Restaurant restaurant = findRestaurant(restaurantId);

        restaurant.update(restaurantDto);
        return restaurant.getId();
    }

    @Override
    @Transactional
    public Integer likeRestaurants(Long restaurantId) {
        User user = SecurityUtil.getUser();
        Restaurant restaurant = findRestaurant(restaurantId);

         if(restaurantLikesRepository.existsByUserAndRestaurant(user, restaurant)) {
             throw new CustomException(ErrorCode.RESTAURANT_LIKE_ALREADY_EXIST, "이미 해당 식당을 추천했습니다. ");
         }

        RestaurantLikes restaurantLikes = RestaurantLikes.toEntity(user, restaurant);
        restaurantLikesRepository.save(restaurantLikes);

        restaurant.plusLikeCount();

        return restaurant.getLikeCount();
    }

    @Override
    public Integer dislikeRestaurants(Long restaurantId) {
        return null;
    }

    @Override
    @Transactional
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
