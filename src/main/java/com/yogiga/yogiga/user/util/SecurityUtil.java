package com.yogiga.yogiga.user.util;

import com.yogiga.yogiga.global.exception.CustomException;
import com.yogiga.yogiga.global.exception.ErrorCode;
import com.yogiga.yogiga.user.entity.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
@Slf4j
@NoArgsConstructor
public class SecurityUtil {
    public static User getUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND_ERROR, "존재하지 않는 회원입니다. ");
        }
        log.info("user={}", user);
        return user;
    }

}
