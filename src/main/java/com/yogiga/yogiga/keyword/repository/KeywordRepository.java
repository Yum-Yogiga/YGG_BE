package com.yogiga.yogiga.keyword.repository;

import com.yogiga.yogiga.keyword.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    List<Keyword> getKeywordsById(Long id);

    List<Keyword> findAllByOrderByIdDesc();
}
