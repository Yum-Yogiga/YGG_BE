package com.yogiga.yogiga.user.repository;

import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.enums.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByNickname(String nickname);
    Optional<User> findByUserId(String userId);

    Optional<User> findByEmailAndSocialType(String email, SocialType socialType);

}
