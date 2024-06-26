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
import org.springframework.web.bind.annotation.PostMapping;
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

    @Operation(summary = "토큰 재발행", description = "리프레시 토큰을 통해 토큰을 재발행 받습니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @PostMapping("/reissue")
    public ApiResponseDto<JwtToken> issueToken(@RequestParam String refresh) {
        return ApiResponseDto.onSuccess(tokenService.issueTokens(refresh));
    }
}
