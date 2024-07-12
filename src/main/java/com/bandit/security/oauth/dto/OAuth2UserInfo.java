package com.bandit.security.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract String getOAuth2Id();

    public abstract String getEmail();

    public abstract String getNickname();

    public abstract String getProfileImg();
}