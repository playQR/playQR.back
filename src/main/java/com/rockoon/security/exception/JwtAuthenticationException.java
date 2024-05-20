package com.rockoon.security.exception;

import com.rockoon.presentation.payload.code.ErrorStatus;
import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {
    public JwtAuthenticationException(ErrorStatus errorStatus) {
        super(errorStatus.name());
    }
}
