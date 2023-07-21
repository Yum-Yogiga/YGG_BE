package com.yogiga.yogiga.keyword.controller;

import com.yogiga.yogiga.keyword.dto.KeywordDto;
import com.yogiga.yogiga.keyword.service.KeywordService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/keywords")
public class KeywordController {
    private final KeywordService keywordService;

    @Operation(summary = "모든 키워드 List로 조회")
    @GetMapping("/all")
    public ResponseEntity<List<KeywordDto>> getAllRes() {
        return ResponseEntity.ok(keywordService.getAllKeywords());
    }
}
