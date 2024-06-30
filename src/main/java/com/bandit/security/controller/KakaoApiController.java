package com.bandit.security.controller;

import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.dto.KakaoFriendDto;
import com.bandit.global.service.KakaoService;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Kakao Outer API", description = "카카오 외부 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/kakao")
public class KakaoApiController {
    private final KakaoService kakaoService;
    @Operation(summary = "카카오 친구 목록 가져오기", description = "리소스에서 받아온 액세스 토큰을 통해 카카오 친구 목록을 가져옵니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @GetMapping("/list")
    public ApiResponseDto<List<KakaoFriendDto>> getFriendsList(@RequestParam String accessToken) {
        return ApiResponseDto.onSuccess(kakaoService.getFriendsList(accessToken));
    }
}
