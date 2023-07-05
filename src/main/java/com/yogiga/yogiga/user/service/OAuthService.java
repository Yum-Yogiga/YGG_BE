package com.yogiga.yogiga.user.service;

import com.yogiga.yogiga.user.dto.UserDto;
import com.yogiga.yogiga.user.entity.User;
import com.yogiga.yogiga.user.enums.OAuthAttributes;
import com.yogiga.yogiga.user.enums.SocialType;
import com.yogiga.yogiga.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OAuthService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest); //OAuth 서비스에서 가져온 유저 정보

        String registrationId = userRequest.getClientRegistration().getRegistrationId();//OAuth 서비스 이름

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails()
                .getUserInfoEndpoint()
                .getUserNameAttributeName(); //OAuth 로그인시 pk가 되는 값, 네이버 카카오는 지원 x

        Map<String, Object> attributes = oAuth2User.getAttributes(); //OAuth 서비스의 유저 정보들

        UserDto userDto = OAuthAttributes.extract(registrationId, attributes); // registrationId에 따라 유저 정보를 통해 공통된 UserDto 객체로 만들
        userDto.setSocialType(SocialType.valueOf(registrationId));
        User user = saveOrUpdate(userDto);

        Map<String, Object> customAttribute = customAttribute(attributes, userNameAttributeName, userDto, registrationId);

        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority("USER")),
                customAttribute,
                userNameAttributeName
        );
    }

    private Map customAttribute(Map attributes, String userNameAttributeName, UserDto userDto, String registrationId) {
        Map<String, Object> customAttribute = new LinkedHashMap<>();
        customAttribute.put(userNameAttributeName, attributes.get(userNameAttributeName));
        customAttribute.put("email", userDto.getEmail());
        customAttribute.put("userId", userDto.getUserId());
        customAttribute.put("socialType", userDto.getSocialType());
        customAttribute.put("nickname", userDto.getNickname());
        return customAttribute;

    }

    private User saveOrUpdate(UserDto userDto) {
        Optional<User> optionalUser = userRepository.findByEmailAndSocialType(userDto.getEmail(), userDto.getSocialType());

        User user;

        if(optionalUser.isEmpty()) {
            user = User.toEntity(userDto);
        }
        else {
            user = optionalUser.get();
            user.update(userDto.getUserId(), userDto.getEmail());
        }
        return userRepository.save(user);
    }
}
