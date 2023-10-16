package com.yogiga.yogiga.restaurant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RestaurantDto {
    @NotBlank(message = "식당 이름을 필수로 입력해야 합니다. ")
    private String name;

    @NotBlank(message = "주소를 필수로 입력해야 합니다. ")
    private String address;

    @Schema(description = "식당 전화번호")
    private String tel;

    @Schema(description = "식당 영업시간")
    private String openingHours;

    @Schema(description = "메뉴 정보들")
    private List<MenuDto> menuDtoList;
}
