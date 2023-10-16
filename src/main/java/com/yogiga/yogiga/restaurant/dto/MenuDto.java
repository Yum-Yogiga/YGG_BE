package com.yogiga.yogiga.restaurant.dto;

import com.yogiga.yogiga.restaurant.entity.Menu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class MenuDto {
    @Schema(description = "메뉴 이름")
    private String name;

    @Schema(description = "메뉴 가격")
    private String price;

    @Schema(description = "메뉴 이미지")
    private MultipartFile image;

    public static MenuDto toDto(Menu menu) {
        return MenuDto.builder()
                .name(menu.getName())
                .price(menu.getPrice())
                .build();
    }
}
