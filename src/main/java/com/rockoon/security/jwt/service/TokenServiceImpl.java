package com.rockoon.security.jwt.service;

import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.service.RedisService;
import com.rockoon.security.jwt.dto.JwtToken;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.Key;

public class TokenServiceImpl implements TokenService{
    private final Key key;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;
    private final MemberQueryService memberQueryService;

    public TokenServiceImpl(@Value("${app.jwt.secret}") String key,
                            AuthenticationManagerBuilder authenticationManagerBuilder,
                            PasswordEncoder passwordEncoder,
                            RedisService redisService,
                            MemberQueryService memberQueryService) {
        byte[] keyBytes = Decoders.BASE64.decode(key);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.passwordEncoder = passwordEncoder;
        this.redisService = redisService;
        this.memberQueryService = memberQueryService;
    }
    @Override
    public JwtToken login(String kakaoEmail) {
        return null;
    }

    @Override
    public JwtToken issueTokens(String refreshToken) {
        return null;
    }

    @Override
    public JwtToken generateToken(Authentication authentication) {
        return null;
    }

    @Override
    public Authentication getAuthentication(String accessToken) {
        return null;
    }

    @Override
    public boolean validateToken(String token) {
        return false;
    }

    @Override
    public boolean logout(String refreshToken) {
        return false;
    }

    @Override
    public boolean existsRefreshToken(String refreshToken) {
        return false;
    }
}
