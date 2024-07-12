package com.bandit.domain.manager.controller;

import com.bandit.domain.manager.service.ManagerCommandService;
import com.bandit.domain.manager.service.ManagerQueryService;
import com.bandit.domain.member.dto.MemberResponse;
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
import java.util.stream.Collectors;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Manager API", description = "매니저 관련 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/manager")
@RequiredArgsConstructor
public class ManagerApiController {

    private final ManagerCommandService managerCommandService;
    private final ManagerQueryService managerQueryService;

    @Operation(summary = "매니저 생성 🔑", description = "프로모션 ID와 멤버 정보를 받아 새로운 매니저를 생성합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.MANAGER_ALREADY_EXISTS,
            ErrorStatus.PROMOTION_NOT_FOUND
    }, status = AUTH)
    @PostMapping("/{promotionId}")
    public ApiResponseDto<Long> createManager(
            @PathVariable Long promotionId,
            @AuthUser Member member) {
        return ApiResponseDto.onSuccess(managerCommandService.createManager(promotionId, member));
    }

    @Operation(summary = "매니저 삭제(v1 사용 x)", description = "프로모션 ID와 멤버 정보를 받아 해당 매니저를 삭제합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @DeleteMapping("/{promotionId}")
    public ApiResponseDto<Boolean> deleteManager(
            @PathVariable Long promotionId,
            @AuthUser Member member) {
        managerCommandService.deleteManager(promotionId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "프로모션 ID로 매니저 조회(v1 사용 x)", description = "프로모션 ID를 받아 해당 프로모션에 속한 모든 매니저 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/promotions/{promotionId}")
    public ApiResponseDto<List<MemberResponse>> getManagersByPromotionId(@PathVariable Long promotionId) {
        List<Member> managers = managerQueryService.getManagers(promotionId);
        List<MemberResponse> managerResponses = managers.stream()
                .map(manager -> MemberResponse.builder()
                        .name(manager.getName())
                        .nickname(manager.getNickname())
                        .profileImg(manager.getProfileImg())
                        .build())
                .collect(Collectors.toList());
        return ApiResponseDto.onSuccess(managerResponses);
    }

    @Operation(summary = "게스트 입장 완료(v1 사용 x)", description = "게스트 ID를 받아 해당 게스트의 입장을 완료합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @PostMapping("/entrance/{guestId}")
    public ApiResponseDto<Boolean> completeEntrance(
            @PathVariable Long guestId,
            @AuthUser Member member) {
        managerCommandService.completeEntrance(guestId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "게스트 예약 취소(v1 사용 x)", description = "게스트 ID를 받아 해당 게스트의 예약을 취소합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @PostMapping("/cancel-reservation/{guestId}")
    public ApiResponseDto<Boolean> cancelReservation(
            @PathVariable Long guestId,
            @AuthUser Member member) {
        managerCommandService.cancelReservation(guestId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "게스트 예약 확정(v1 사용 x)", description = "게스트 ID를 받아 해당 게스트의 예약을 확정합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @PostMapping("/confirm-reservation/{guestId}")
    public ApiResponseDto<Boolean> confirmReservation(
            @PathVariable Long guestId,
            @AuthUser Member member) {
        managerCommandService.confirmReservation(guestId, member);
        return ApiResponseDto.onSuccess(true);
    }
}