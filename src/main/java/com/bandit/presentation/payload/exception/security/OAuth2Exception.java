package com.bandit.presentation.payload.exception.security;

import com.bandit.presentation.payload.code.ErrorStatus;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;

public class OAuth2Exception extends OAuth2AuthenticationException {
    public OAuth2Exception(ErrorStatus errorStatus) {
        super(errorStatus.name());
    }
}
