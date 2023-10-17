package com.yogiga.yogiga.review.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    @Schema(description = "REVIEW 내용")
    @NotBlank(message = "리뷰를 필수로 입력해야 합니다. ")
    String content;
}
