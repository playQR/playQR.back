package com.bandit.presentation.payload.exception.security;

import com.bandit.presentation.payload.code.ErrorStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(ErrorStatus errorStatus) {
        super(errorStatus.name());
    }
}
