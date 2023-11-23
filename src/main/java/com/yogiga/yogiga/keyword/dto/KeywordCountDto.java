package com.yogiga.yogiga.keyword.dto;

import com.yogiga.yogiga.keyword.entity.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordCountDto {

    @Schema(description = "키워드 이름")
    private String name;

    @Schema(description = "키워드 선택수")
    private int value;

    public static KeywordCountDto toDto(String name, int count){
        return KeywordCountDto.builder()
                .name(name)
                .value(count)
                .build();
    }
}
