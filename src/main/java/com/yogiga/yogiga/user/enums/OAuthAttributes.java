package com.yogiga.yogiga.user.enums;

import com.yogiga.yogiga.user.dto.UserDto;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;

public enum OAuthAttributes {
    GOOGLE("google", (attributes) -> {
        UserDto userDto = new UserDto();
        userDto.setEmail((String) attributes.get("email"));
        userDto.setUserId((String) attributes.get("email"));
        userDto.setNickname((String) attributes.get("email"));
        return userDto;
    }),

    NAVER("naver", (attributes) -> {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        System.out.println(response);
        UserDto userDto = new UserDto();
        userDto.setEmail((String) response.get("email"));
        userDto.setUserId(((String) response.get("email")));
        return userDto;
    }),

    KAKAO("kakao", (attributes) -> {
        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        UserDto userDto = new UserDto();
        userDto.setNickname((String) kakaoProfile.get("nickname"));
        userDto.setEmail((String) kakaoAccount.get("email"));
        return userDto;
    });


    private final String registrationId;
    private final Function<Map<String, Object>, UserDto> of;

    OAuthAttributes(String registrationId, Function<Map<String, Object>, UserDto> of) {
        this.registrationId = registrationId;
        this.of = of;
    }

    public static UserDto extract(String registrationId, Map<String, Object> attributes) {
        return Arrays.stream(values())
                .filter(provider -> registrationId.equals(provider.registrationId))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new)
                .of.apply(attributes);
    }

}
