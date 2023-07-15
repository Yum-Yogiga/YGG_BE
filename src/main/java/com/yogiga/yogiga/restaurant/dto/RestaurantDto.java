package com.yogiga.yogiga.restaurant.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestaurantDto {
    @NotBlank(message = "식당 이름을 필수로 입력해야 합니다. ")
    private String name;

    @NotBlank(message = "주소를 필수로 입력해야 합니다. ")
    private String address;

    private String tel;

    private String openingHours;
}
