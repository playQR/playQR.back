package com.rockoon.security.oauth.factory;

import com.rockoon.security.oauth.dto.KakaoOAuth2User;
import com.rockoon.security.oauth.dto.OAuth2UserInfo;
import lombok.Getter;

import java.util.Map;

public class OAuth2UserInfoFactory {
    public static OAuth2UserInfo getOAuth2UserInfo(AuthProvider authProvider, Map<String, Object> attributes) {
        return new KakaoOAuth2User(attributes);
    }

    @Getter
    public enum AuthProvider {
        KAKAO
    }
}
