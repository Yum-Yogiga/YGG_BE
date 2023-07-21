package com.yogiga.yogiga.keyword.service;

import com.yogiga.yogiga.keyword.dto.KeywordDto;
import com.yogiga.yogiga.keyword.dto.UserKeywordDto;

import java.util.List;

public interface KeywordService {
    List<KeywordDto> getAllKeywords();

    Long saveUserKeywords(String userId, UserKeywordDto userKeywordDto);
}