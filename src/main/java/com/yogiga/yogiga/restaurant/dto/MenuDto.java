package com.yogiga.yogiga.restaurant.dto;

import com.yogiga.yogiga.restaurant.entity.Menu;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MenuDto {
    private String name;

    private double price;

    public static MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }
}
