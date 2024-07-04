package com.bandit.api.kakao.controller;

import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.api.kakao.dto.KakaoFriendDto;
import com.bandit.api.kakao.dto.KakaoMessageRequest;
import com.bandit.api.kakao.service.KakaoService;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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

    @Operation(summary = "카카오 친구에게 메세지 보내기",
            description = "카카오 친구 목록에서 받아온 uuid를 통해 원하는 카카오 친구에게 메세지를 보냅니다")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR
    })
    @PostMapping("/message")
    public ApiResponseDto<Boolean> sendKakaoMessage(@RequestParam String accessToken,
                                                 @RequestBody KakaoMessageRequest kakaomessageRequest) {
        kakaoService.sendMessage(accessToken, kakaomessageRequest);
        return ApiResponseDto.onSuccess(true);
    }
}
