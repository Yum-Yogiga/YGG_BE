package com.yogiga.yogiga.user.controller;

import com.yogiga.yogiga.keyword.dto.UserKeywordDto;
import com.yogiga.yogiga.keyword.service.KeywordService;
import com.yogiga.yogiga.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final KeywordService keywordService;
    @Operation(summary = "회원 선호 키워드 등록 api")
    @PostMapping("/{userId}/preferred-keywords")
    public Long selectKeywords(@PathVariable String userId, @Valid @RequestBody UserKeywordDto userKeywordDto) {
        return keywordService.saveUserKeywords(userId, userKeywordDto);
    }

}
