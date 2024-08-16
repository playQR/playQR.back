package com.bandit.domain.board.controller;

import com.bandit.domain.board.converter.PromotionConverter;
import com.bandit.domain.board.dto.promotion.PromotionResponse;
import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;
import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionListDto;
import com.bandit.domain.board.service.promotion.PromotionCommandService;
import com.bandit.domain.board.service.promotion.PromotionQueryService;
import com.bandit.domain.like.service.like_music.LikeMusicQueryService;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
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

@Tag(name = "Promotion API", description = "프로모션 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RequestMapping("/api/promotions/v2")
@RequiredArgsConstructor
@RestController
public class PromotionApiV2Controller {
    private final PromotionCommandService promotionCommandService;
    private final PromotionQueryService promotionQueryService;
    private final LikeMusicQueryService likeMusicQueryService;

    @Operation(summary = "프로모션 조회(비인증)", description = "로그인하지 않은 유저가 프로모션의 PK를 통해 글을 조회합니다.")
    @ApiErrorCodeExample({
            ErrorStatus.PROMOTION_NOT_FOUND
    })
    @GetMapping("/{promotionId}")
    public ApiResponseDto<PromotionDetailDto> getPromotionById(@PathVariable Long promotionId) {
        PromotionDetailDto detailDto = PromotionConverter.toDetailDto(promotionQueryService.getPromotionById(promotionId));
        likeMusicQueryService.countLike(detailDto);
        return ApiResponseDto.onSuccess(detailDto);
    }
    @Operation(summary = "프로모션 조회(인증🔑)", description = "로그인한 유저가 프로모션의 PK를 통해 글을 조회합니다.")
    @ApiErrorCodeExample({
            ErrorStatus.PROMOTION_NOT_FOUND
    })
    @GetMapping("/{promotionId}")
    public ApiResponseDto<PromotionDetailDto> getPromotionById(@PathVariable Long promotionId) {
        PromotionDetailDto detailDto = PromotionConverter.toDetailDto(promotionQueryService.getPromotionById(promotionId));
        likeMusicQueryService.countLike(detailDto);
        return ApiResponseDto.onSuccess(detailDto);
    }

    @Operation(summary = "프로모션 페이징 조회(비인증)", description = "로그인하지 않은 유저가 프로모션의 리스트를 페이징을 통해 조회합니다." +
            "한페이지당 사이즈는 10개입니다.")
    @ApiErrorCodeExample
    @GetMapping
    public ApiResponseDto<PromotionListDto> getPromotionList(@RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, PageUtil.PROMOTION_SIZE);
        return ApiResponseDto.onSuccess(
                PromotionConverter.toListDto(
                        promotionQueryService.getPaginationPromotion(pageable)
                )
        );
    }
    @Operation(summary = "프로모션 페이징 조회(인증🔑)", description = "프로모션의 리스트를 페이징을 통해 조회합니다." +
            "한페이지당 사이즈는 10개입니다.")
    @ApiErrorCodeExample
    @GetMapping
    public ApiResponseDto<PromotionListDto> getPromotionList(@RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, PageUtil.PROMOTION_SIZE);
        return ApiResponseDto.onSuccess(
                PromotionConverter.toListDto(
                        promotionQueryService.getPaginationPromotion(pageable)
                )
        );
    }

}
