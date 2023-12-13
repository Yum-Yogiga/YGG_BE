package com.yogiga.yogiga.restaurant.dto;

import com.yogiga.yogiga.keyword.dto.KeywordCountDto;
import com.yogiga.yogiga.restaurant.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "식당 응답 DTO")
public class RestaurantResponseDto {

    @NotBlank
    private String name;

    private String link;

    @NotBlank
    private String address;

    private String tel;

    private String openingHours;

    private Integer likeCount;

    private Integer dislikeCount;

    private Double latitude; // 위도
    private Double longitude; // 경도

    private List<KeywordCountDto> keywordCounts;

    private List<MenuResponseDto> menuList;

    @Builder
    public static RestaurantResponseDto toDto(Restaurant restaurant) {
        return RestaurantResponseDto.builder()
                .name(restaurant.getName())
                .link(restaurant.getLink())
                .address(restaurant.getAddress())
                .tel(restaurant.getTel())
                .openingHours(restaurant.getOpeningHours())
                .likeCount(restaurant.getLikeCount())
                .latitude(restaurant.getLatitude())
                .longitude(restaurant.getLongitude())
                .dislikeCount(restaurant.getDislikeCount())
                .menuList(restaurant.getMenuList().stream()
                        .map(MenuResponseDto::toDto)
                        .collect(Collectors.toList()))
                .build();
    }

    public void setTopKeywordCount(List<KeywordCountDto> topKeywords) {
        this.keywordCounts = topKeywords;
    }
}
