package com.yogiga.yogiga.keyword.repository;

import com.yogiga.yogiga.keyword.entity.UserKeyword;
import com.yogiga.yogiga.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserKeywordRepository extends JpaRepository<UserKeyword, Long> {
    void deleteByUser(User user);
}
