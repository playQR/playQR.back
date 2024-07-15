package com.bandit.domain.like.controller;

import com.bandit.domain.board.converter.PromotionConverter;
import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionListDto;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.like.service.like_promotion.LikePromotionCommandService;
import com.bandit.domain.like.service.like_promotion.LikePromotionQueryService;
import com.bandit.domain.member.entity.Member;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.global.util.PageUtil;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "LikePromotion API", description = "프로모션 좋아요 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/likes/promotion")
@RequiredArgsConstructor
@RestController
public class LikePromotionApiController {
    private final LikePromotionQueryService likePromotionQueryService;
    private final LikePromotionCommandService likePromotionCommandService;

    @Operation(summary = "프로모션 좋아요 🔑", description = "로그인한 회원이 프로모션 좋아요를 누릅니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.PROMOTION_NOT_FOUND,
            ErrorStatus.LIKE_ALREADY_EXIST
    }, status = AUTH)
    @PostMapping("/{promotionId}")
    public ApiResponseDto<Long> likeSetList(@AuthUser Member member,
                                            @PathVariable Long promotionId) {
        Long likeId = likePromotionCommandService.likePromotion(promotionId, member);
        return ApiResponseDto.onSuccess(likeId);
    }

    @Operation(summary = "프로모션 좋아요 취소 🔑", description = "로그인한 회원이 프로모션의 좋아요를 취소합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.LIKE_NOT_FOUND
    }, status = AUTH)
    @DeleteMapping("/{promotionId}")
    public ApiResponseDto<Boolean> unlikeSetList(@AuthUser Member member,
                                                 @PathVariable Long promotionId) {
        likePromotionCommandService.unlikePromotion(promotionId, member);
        return ApiResponseDto.onSuccess(true);
    }

    @Operation(summary = "프로모션 좋아요 확인 🔑", description = "로그인한 회원이 프로모션을 좋아요 했는지 확인합니다.")
    @ApiErrorCodeExample(status = AUTH)
    @GetMapping("/{promotionId}")
    public ApiResponseDto<Boolean> checkIsLiked(@AuthUser Member member,
                                                @PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(likePromotionQueryService.isLiked(promotionId, member));
    }

    @Operation(summary = "프로모션 좋아요 개수 확인", description = "프로모션 좋아요 개수를 확인합니다.")
    @ApiErrorCodeExample
    @GetMapping("/{promotionId}/count")
    public ApiResponseDto<Long> countLike(@PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(likePromotionQueryService.countLike(promotionId));
    }

    @Operation(summary = "내가 좋아요한 프로모션 페이징 조회 🔑", description = "로그인한 회원이 좋아요했던 프로모션들을 리턴합니다.")
    @ApiErrorCodeExample(status = AUTH)
    @GetMapping("/promotions")
    public ApiResponseDto<PromotionListDto> getMyFavoritePromotions(@AuthUser Member member,
                                                                    @RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, PageUtil.PROMOTION_SIZE);
        Page<Promotion> paginationPromotion =  likePromotionQueryService.getMyFavoritePromotionList(member, pageable);
        return ApiResponseDto.onSuccess(PromotionConverter.toListDto(paginationPromotion));
    }
}
