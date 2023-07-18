package com.yogiga.yogiga.keyword.service;

import com.yogiga.yogiga.keyword.dto.UserKeywordDto;
import com.yogiga.yogiga.keyword.entity.Keyword;

import java.util.List;

public interface KeywordService {
    List<Keyword> getKeywordsByIds(List<Long> keywordIds);

    Long saveUserKeywords(String userId, UserKeywordDto userKeywordDto);
}