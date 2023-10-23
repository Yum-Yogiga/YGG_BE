package com.yogiga.yogiga.restaurant.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

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

}
