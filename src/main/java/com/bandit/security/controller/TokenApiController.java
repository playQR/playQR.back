package com.bandit.security.controller;

import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import com.bandit.security.jwt.dto.JwtToken;
import com.bandit.security.jwt.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Token API", description = "토큰 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequiredArgsConstructor
@RequestMapping("/api/tokens")
@RestController
public class TokenApiController {
    private final TokenService tokenService;

    @Operation(summary = "로그인(토큰 생성)", description = "카카오이메일을 통해 로그인합니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @GetMapping("/login")
    public ApiResponseDto<JwtToken> login(@RequestParam String kakaoEmail) {
        return ApiResponseDto.onSuccess(tokenService.login(kakaoEmail));
    }
}
