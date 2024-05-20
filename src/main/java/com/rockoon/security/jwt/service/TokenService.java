package com.rockoon.security.jwt.service;

import com.rockoon.security.jwt.dto.JwtToken;
import org.springframework.security.core.Authentication;

public interface TokenService {
    JwtToken login(String kakaoEmail);

    JwtToken issueTokens(String refreshToken);

    JwtToken generateToken(Authentication authentication);

    Authentication getAuthentication(String accessToken);

    boolean validateToken(String token);

    boolean logout(String refreshToken);

    boolean existsRefreshToken(String refreshToken);
}
