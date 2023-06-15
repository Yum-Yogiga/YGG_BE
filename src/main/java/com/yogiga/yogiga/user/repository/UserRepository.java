package com.yogiga.yogiga.user.repository;

import com.yogiga.yogiga.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getByUserId(String userId);

    User getByEmail(String email);

    User getByNickname(String nickname);
}
