package com.bandit.domain.board.controller;

import com.bandit.domain.board.converter.PromotionConverter;
import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;
import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionListDto;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.like.service.like_music.LikeMusicQueryService;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Promotion API V2", description = "프로모션 API V2")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/v2/promotions")
@RequiredArgsConstructor
@RestController
public class PromotionApiV2Controller {
    private final PromotionQueryService promotionQueryService;
    private final LikeMusicQueryService likeMusicQueryService;
    private final LikePromotionQueryService likePromotionQueryService;

    @Operation(summary = "프로모션 조회(비인증)", description = "로그인하지 않은 유저가 프로모션의 PK를 통해 글을 조회합니다.")
    @ApiErrorCodeExample({
            ErrorStatus.PROMOTION_NOT_FOUND
    })
    @GetMapping("/{promotionId}")
    public ApiResponseDto<PromotionDetailDto> getPromotionById(@PathVariable Long promotionId) {
        PromotionDetailDto detailDto = PromotionConverter
                .toDetailDto(promotionQueryService.getPromotionById(promotionId));
        PromotionConverter.setPromotionLikeInDto(
                detailDto,
                likePromotionQueryService.countLike(promotionId),
                false
        );
        likeMusicQueryService.countLike(detailDto);
        return ApiResponseDto.onSuccess(detailDto);
    }

    @Operation(summary = "프로모션 조회(인증🔑)", description = "로그인한 유저가 프로모션의 PK를 통해 글을 조회합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.PROMOTION_NOT_FOUND
    }, status = AUTH)
    @GetMapping("/{promotionId}/auth")
    public ApiResponseDto<PromotionDetailDto> getPromotionById_auth(@AuthUser Member member,
                                                                    @PathVariable Long promotionId) {
        PromotionDetailDto detailDto = PromotionConverter
                .toDetailDto(promotionQueryService.getPromotionById(promotionId));
        PromotionConverter.setPromotionLikeInDto(
                detailDto,
                likePromotionQueryService.countLike(promotionId),
                likePromotionQueryService.isLiked(promotionId, member)
        );
        likeMusicQueryService.isLiked(detailDto, member);
        likeMusicQueryService.countLike(detailDto);
        return ApiResponseDto.onSuccess(detailDto);
    }

    @Operation(summary = "프로모션 페이징 조회(비인증)", description = "로그인하지 않은 유저가 프로모션의 리스트를 페이징을 통해 조회합니다." +
            "한페이지당 사이즈는 10개입니다.")
    @ApiErrorCodeExample
    @GetMapping
    public ApiResponseDto<PromotionListDto> getPromotionList(@RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, PageUtil.PROMOTION_SIZE);
        PromotionListDto listDto = PromotionConverter.toListDto(
                promotionQueryService.getPaginationPromotion(pageable)
        );
        listDto.getPromotionList().forEach(dto -> PromotionConverter.setPromotionLikeInDto(dto,
                likePromotionQueryService.countLike(dto.getPromotionId()),
                false));
        return ApiResponseDto.onSuccess(listDto);
    }

    @Operation(summary = "프로모션 페이징 조회(인증🔑)", description = "프로모션의 리스트를 페이징을 통해 조회합니다." +
            "한페이지당 사이즈는 10개입니다.")
    @ApiErrorCodeExample(status = AUTH)
    @GetMapping("/auth")
    public ApiResponseDto<PromotionListDto> getPromotionList_auth(@AuthUser Member member,
                                                                  @RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, PageUtil.PROMOTION_SIZE);
        PromotionListDto listDto = PromotionConverter.toListDto(
                promotionQueryService.getPaginationPromotion(pageable)
        );
        listDto.getPromotionList().forEach(dto -> PromotionConverter.setPromotionLikeInDto(dto,
                likePromotionQueryService.countLike(dto.getPromotionId()),
                likePromotionQueryService.isLiked(dto.getPromotionId(), member)));
        return ApiResponseDto.onSuccess(listDto);
    }



}
