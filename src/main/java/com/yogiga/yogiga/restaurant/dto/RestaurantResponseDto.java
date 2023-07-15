package com.yogiga.yogiga.restaurant.dto;

import com.yogiga.yogiga.restaurant.entity.Restaurant;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "식당 응답 DTO")
public class RestaurantResponseDto {

    @NotBlank
    private String name;

    @NotBlank
    private String address;

    private String tel;

    private String openingHours;

    @Builder
    public static RestaurantResponseDto toDto(Restaurant restaurant) {
        return RestaurantResponseDto.builder()
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .tel(restaurant.getTel())
                .openingHours(restaurant.getOpeningHours())
                .build();
    }
}
