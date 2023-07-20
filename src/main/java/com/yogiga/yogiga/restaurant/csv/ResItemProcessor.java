package com.yogiga.yogiga.restaurant.csv;

import com.yogiga.yogiga.keyword.entity.Keyword;
import com.yogiga.yogiga.keyword.entity.RestaurantKeyword;
import com.yogiga.yogiga.keyword.repository.KeywordRepository;
import com.yogiga.yogiga.restaurant.entity.Menu;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ResItemProcessor implements ItemProcessor<ResCsvDto, Restaurant> {

    private EntityManager entityManager;
    private final KeywordRepository keywordRepository;

    @Override
    public Restaurant process(ResCsvDto resCsvDto) throws Exception {
        Restaurant restaurant = Restaurant.builder()
                .name(resCsvDto.getName())
                .address(resCsvDto.getAddress())
                .tel(resCsvDto.getTel())
                .build();

        List<Menu> menuList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String menuName = resCsvDto.getMenuName(i);
            String menuPrice = resCsvDto.getMenuPrice(i);
            if (menuName != null && !menuName.isEmpty() && !menuPrice.isEmpty()) {
                Menu menu = Menu.builder()
                        .name(menuName)
                        .price(menuPrice)
                        .restaurant(restaurant)
                        .build();
                menuList.add(menu);
            }
        }
        restaurant.setMenuList(menuList);

        // Process RestaurantKeyword data and save to RestaurantKeyword table
        List<RestaurantKeyword> restaurantKeywordList = new ArrayList<>();
        Map<String, Double> keywordScores = new HashMap<>();
        keywordScores.put("음식이 맛있어요", resCsvDto.getTasty());
        keywordScores.put("친절해요", resCsvDto.getFriendly());
        keywordScores.put("특별한 메뉴가 있어요", resCsvDto.getSpecialMenu());
        keywordScores.put("매장이 청결해요", resCsvDto.getClean());
        keywordScores.put("재료가 신선해요", resCsvDto.getFreshIngredients());
        keywordScores.put("가성비가 좋아요", resCsvDto.getCostEffective());
        keywordScores.put("양이 많아요", resCsvDto.getGenerousPortions());
        keywordScores.put("인테리어가 멋져요", resCsvDto.getGreatInterior());
        keywordScores.put("혼밥하기 좋아요", resCsvDto.getGoodForSolo());

        keywordScores.forEach((keywordName, score) -> {
            Keyword keyword = keywordRepository.findByName(keywordName);
            if (keyword != null) {
                RestaurantKeyword restaurantKeyword = new RestaurantKeyword();
                restaurantKeyword.setRestaurantKeyword(restaurant, keyword, score);
                restaurantKeywordList.add(restaurantKeyword);
            }
        });
        restaurant.setRestaurantKeywords(restaurantKeywordList);

        return restaurant;
    }
}
