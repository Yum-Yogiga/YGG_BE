package com.yogiga.yogiga.keyword.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserKeywordDto {
    @Schema(description = "키워드 아이디 리스트")
    private List<Long> keywordIds;
}
