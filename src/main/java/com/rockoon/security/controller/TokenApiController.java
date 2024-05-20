package com.rockoon.security.controller;

import com.rockoon.presentation.payload.dto.ApiResponseDto;
import com.rockoon.security.jwt.dto.JwtToken;
import com.rockoon.security.jwt.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @GetMapping("/login")
    public ApiResponseDto<JwtToken> login(@RequestParam String kakaoEmail) {
        return ApiResponseDto.onSuccess(tokenService.login(kakaoEmail));
    }
}
