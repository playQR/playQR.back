package com.bandit.security.oauth.factory;

import com.bandit.security.oauth.dto.KakaoOAuth2User;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static KakaoOAuth2User getOAuth2UserInfo(Map<String, Object> attributes) {
        return new KakaoOAuth2User(attributes);
    }

}
