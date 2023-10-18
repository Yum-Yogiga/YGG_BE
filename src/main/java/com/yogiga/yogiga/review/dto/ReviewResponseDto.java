package com.yogiga.yogiga.review.dto;

import com.yogiga.yogiga.review.entity.Review;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "리뷰 응답 DTO")
public class ReviewResponseDto {
    @Schema(description = "작성자 닉네임")
    String userNickname;

    @Schema(description = "REVIEW 내용")
    String content;

    public static ReviewResponseDto toDto(Review review) {
        return ReviewResponseDto.builder()
                .userNickname(review.getUser().getNickname())
                .content(review.getContent())
                .build();
    }
}
