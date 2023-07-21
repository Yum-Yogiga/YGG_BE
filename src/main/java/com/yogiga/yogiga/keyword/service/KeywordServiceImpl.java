package com.yogiga.yogiga.keyword.service;

import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.keyword.dto.KeywordDto;
import com.yogiga.yogiga.keyword.dto.UserKeywordDto;
import com.yogiga.yogiga.keyword.entity.Keyword;
import com.yogiga.yogiga.keyword.entity.UserKeyword;
import com.yogiga.yogiga.keyword.repository.KeywordRepository;
import com.yogiga.yogiga.keyword.repository.UserKeywordRepository;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.repository.UserRepository;
import com.yogiga.yogiga.user.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KeywordServiceImpl implements KeywordService {
    private final KeywordRepository keywordRepository;
    private final UserKeywordRepository userKeywordRepository;
    private final UserRepository userRepository;


    @Override
    public List<KeywordDto> getAllKeywords() {
        List<Keyword> keywords = keywordRepository.findAllByOrderByIdDesc();
        return keywords.stream().map(KeywordDto::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Long saveUserKeywords(String userId, UserKeywordDto userKeywordDto) {
        User user = SecurityUtil.getUser();

        List<Keyword> keywordList = keywordRepository.findAllById(userKeywordDto.getKeywordIds());
        List<UserKeyword> userKeywords = keywordList.stream()
                .map(keyword -> new UserKeyword(user, keyword)).toList();

        if (userKeywords.isEmpty()) {
            throw new CustomException(ErrorCode.EMPTY_KEYWORD_LIST, "선택한 키워드가 없습니다.");
        }

        userKeywordRepository.deleteByUser(user); // 기존의 UserKeyword 삭제
        userKeywordRepository.saveAll(userKeywords);

        return user.getId();
    }


}
