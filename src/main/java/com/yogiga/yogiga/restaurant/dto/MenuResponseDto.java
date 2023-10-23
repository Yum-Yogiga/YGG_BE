package com.yogiga.yogiga.restaurant.dto;

import com.yogiga.yogiga.restaurant.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class MenuResponseDto {
    @Schema(description = "메뉴 이름")
    private String name;

    @Schema(description = "메뉴 가격")
    private String price;

    @Schema(description = "메뉴 이미지")
    private String imageUrl;

    public static MenuResponseDto toDto(Menu menu) {
        return MenuResponseDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .imageUrl(menu.getImageUrl())
                .build();
    }
}
