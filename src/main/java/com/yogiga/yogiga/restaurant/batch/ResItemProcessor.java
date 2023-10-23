package com.yogiga.yogiga.restaurant.batch;

import com.yogiga.yogiga.keyword.entity.Keyword;
import com.yogiga.yogiga.keyword.entity.RestaurantKeyword;
import com.yogiga.yogiga.keyword.repository.KeywordRepository;
import com.yogiga.yogiga.keyword.repository.RestaurantKeywordRepository;
import com.yogiga.yogiga.restaurant.dto.ResCsvDto;
import com.yogiga.yogiga.restaurant.dto.RestaurantDto;
import com.yogiga.yogiga.restaurant.entity.Menu;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import com.yogiga.yogiga.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ResItemProcessor implements ItemProcessor<ResCsvDto, Restaurant> {
    private final KeywordRepository keywordRepository;
    private final RestaurantRepository restaurantRepository;
    private final RestaurantKeywordRepository restaurantKeywordRepository;

    @Override
    @Transactional
    public Restaurant process(ResCsvDto resCsvDto) throws Exception {

        Restaurant existingRestaurant = restaurantRepository.findByAddress(resCsvDto.getAddress());

        if (existingRestaurant != null) {
            // 메뉴 업데이트
            updateMenu(existingRestaurant, resCsvDto);

            // 키워드 생성 및 추가
            updateKeywords(existingRestaurant, resCsvDto);

            // 식당 업데이트 저장
            return restaurantRepository.save(existingRestaurant);
        } else {
            // DB에 없는 식당인 경우 새로 생성
            Restaurant newRestaurant = createRestaurant(resCsvDto);

            // 새로 생성한 식당 저장
            return restaurantRepository.save(newRestaurant);
        }
    }

    @Transactional
    protected void updateMenu(Restaurant restaurant, ResCsvDto resCsvDto) {
        List<Menu> menuList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String menuName = resCsvDto.getMenuName(i);
            String menuPrice = resCsvDto.getMenuPrice(i);
            String menuImage = resCsvDto.getMenuImage(i);

            if (menuName != null && !menuName.isEmpty() && !menuPrice.isEmpty()) {
                Menu menu = Menu.builder()
                        .name(menuName)
                        .price(menuPrice)
                        .restaurant(restaurant)
                        .imageUrl(menuImage)
                        .build();
                menuList.add(menu);
            }

        }
        restaurant.update(new RestaurantDto(resCsvDto.getName(), resCsvDto.getAddress(), resCsvDto.getTel(), "0", null));
        restaurant.setMenuList(menuList);
    }
    @Transactional
    protected void updateKeywords(Restaurant restaurant, ResCsvDto resCsvDto) {
        List<RestaurantKeyword> existingKeywords = restaurant.getRestaurantKeywords();
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

        for (Map.Entry<String, Double> entry : keywordScores.entrySet()) {
            String keywordName = entry.getKey();
            Double score = entry.getValue();
            Keyword keyword = keywordRepository.findByName(keywordName);
            if (keyword == null) {
                // 키워드가 존재하지 않는 경우 새로 생성하여 DB에 저장
                Keyword keyword1 = new Keyword();
                keyword1.setName(keywordName);
                Keyword newKeyword = keywordRepository.save(keyword1);
                //Restaurant 해당 키워드 점수 저장
                RestaurantKeyword restaurantKeyword = new RestaurantKeyword();
                restaurantKeyword.setRestaurantKeyword(restaurant, newKeyword, score);
                restaurantKeywordRepository.save(restaurantKeyword);
            } else {
                RestaurantKeyword updateResKeyword = restaurantKeywordRepository.findByRestaurantAndKeyword(restaurant, keyword);
                if(updateResKeyword == null){
                    updateResKeyword = new RestaurantKeyword();
                    updateResKeyword.setRestaurantKeyword(restaurant, keyword, score);
                    restaurantKeywordRepository.save(updateResKeyword);
                }
                updateResKeyword.updateScore(score);
            }
        }
    }
    @Transactional
    protected Restaurant createRestaurant(ResCsvDto resCsvDto) {
        Restaurant restaurant = Restaurant.builder()
                .name(resCsvDto.getName())
                .link("https://map.naver.com/p/entry/place/" + resCsvDto.getLink())
                .address(resCsvDto.getAddress())
                .tel(resCsvDto.getTel())
                .likeCount(0)
                .dislikeCount(0)
                .build();
        restaurantRepository.save(restaurant);

        // 메뉴 생성 및 추가
        List<Menu> menuList = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            String menuName = resCsvDto.getMenuName(i);
            String menuPrice = resCsvDto.getMenuPrice(i);
            String menuImage = resCsvDto.getMenuImage(i);

            if (menuName != null && !menuName.isEmpty() && !menuPrice.isEmpty()) {
                Menu menu = Menu.builder()
                        .name(menuName)
                        .price(menuPrice)
                        .restaurant(restaurant)
                        .imageUrl(menuImage)
                        .build();
                menuList.add(menu);
            }
        }
        restaurant.setMenuList(menuList);

        // 키워드 생성 및 추가
        updateKeywords(restaurant, resCsvDto);

        return restaurant;
    }
}
