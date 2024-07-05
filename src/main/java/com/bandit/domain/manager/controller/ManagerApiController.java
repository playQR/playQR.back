package com.bandit.domain.manager.controller;

import com.bandit.domain.manager.service.ManagerCommandService;
import com.bandit.domain.manager.service.ManagerQueryService;
import com.bandit.domain.member.entity.Member;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Manager API", description = "매니저 관련 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerApiController {

    private final ManagerCommandService managerCommandService;
    private final ManagerQueryService managerQueryService;

    @Operation(summary = "매니저 생성", description = "프로모션 ID와 멤버 정보를 받아 새로운 매니저를 생성합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR}
    )
    @PostMapping("/{promotionId")
    public ApiResponseDto<Boolean> createManager(
            @PathVariable Long promotionId,
            @AuthUser Member member) {
        managerCommandService.createManager(promotionId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "매니저 삭제", description = "매니저 ID를 받아 해당 매니저를 삭제합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @DeleteMapping("/{promotionId}")
    public ApiResponseDto<Boolean> deleteManager(
            @PathVariable Long promotionId,
            @AuthUser Member member) {
        managerCommandService.deleteManager(promotionId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "프로모션 ID로 매니저 조회", description = "프로모션 ID를 받아 해당 프로모션에 속한 모든 매니저 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/promotions/{promotionId}")
    public ApiResponseDto<List<Member>> getManagersByPromotionId(
            @PathVariable Long promotionId,
            @AuthUser Member member) {
        List<Member> managers = managerQueryService.getManagers(promotionId);
        return ApiResponseDto.onSuccess(managers);
    }
}