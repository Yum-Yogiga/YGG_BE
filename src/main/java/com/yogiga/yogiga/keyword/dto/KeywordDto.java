package com.yogiga.yogiga.keyword.dto;

import com.yogiga.yogiga.keyword.entity.Keyword;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeywordDto {
    @Schema(description = "키워드 ID")
    private Long id;

    @Schema(description = "키워드 이름")
    private String name;

    public static KeywordDto toDto(Keyword keyword){
        return KeywordDto.builder()
                .id(keyword.getId())
                .name(keyword.getName())
                .build();
    }
}
