package com.bandit.domain.like.controller;

import com.bandit.domain.like.service.like_promotion.LikePromotionCommandService;
import com.bandit.domain.like.service.like_promotion.LikePromotionQueryService;
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

@Tag(name = "LikePromotion API", description = "프로모션 좋아요 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/likes/promotion")
@RequiredArgsConstructor
@RestController
public class LikePromotionApiController {
    private final LikePromotionQueryService likePromotionQueryService;
    private final LikePromotionCommandService likePromotionCommandService;

    @Operation(summary = "프로모션 좋아요 🔑", description = "로그인한 회원이 프로모션 좋아요를 누릅니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR,
            ErrorStatus._ASSIGNABLE_PARAMETER,
            ErrorStatus.MEMBER_NOT_FOUND
    })
    @PostMapping("/{promotionId}")
    public ApiResponseDto<Long> likeSetList(@AuthUser Member member,
                                            @PathVariable Long promotionId) {
        Long likeId = likePromotionCommandService.likePromotion(promotionId, member);
        return ApiResponseDto.onSuccess(likeId);
    }

    @Operation(summary = "프로모션 좋아요 취소 🔑", description = "로그인한 회원이 프로모션의 좋아요를 취소합니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR,
            ErrorStatus._ASSIGNABLE_PARAMETER,
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.LIKE_NOT_FOUND
    })
    @DeleteMapping("/{promotionId}")
    public ApiResponseDto<Boolean> unlikeSetList(@AuthUser Member member,
                                                 @PathVariable Long promotionId) {
        likePromotionCommandService.unlikePromotion(promotionId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "프로모션 좋아요 확인 🔑", description = "로그인한 회원이 프로모션을 좋아요 했는지 확인합니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR,
            ErrorStatus._ASSIGNABLE_PARAMETER,
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.LIKE_NOT_FOUND
    })
    @GetMapping("/{promotionId}")
    public ApiResponseDto<Boolean> checkIsLiked(@AuthUser Member member,
                                                @PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(likePromotionQueryService.isLiked(promotionId, member));
    }

    @Operation(summary = "프로모션 좋아요 개수 확인", description = "프로모션 좋아요 개수를 확인합니다.")
    @ApiErrorCodeExample({
            ErrorStatus._INTERNAL_SERVER_ERROR,
            ErrorStatus._UNAUTHORIZED_LOGIN_DATA_RETRIEVAL_ERROR,
            ErrorStatus._ASSIGNABLE_PARAMETER,
            ErrorStatus.MEMBER_NOT_FOUND,
            ErrorStatus.LIKE_NOT_FOUND
    })
    @GetMapping("/{promotionId}/count")
    public ApiResponseDto<Long> countLike(@PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(likePromotionQueryService.countLike(promotionId));
    }
}
