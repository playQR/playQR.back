package com.rockoon.domain.board.controller;

import com.rockoon.domain.board.converter.PromotionConverter;
import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.dto.promotion.PromotionResponse.PromotionListDto;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.board.service.promotion.PromotionQueryService;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.presentation.payload.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import static com.rockoon.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;

@RequestMapping("/api/promotions")
@RequiredArgsConstructor
@RestController
public class PromotionApiController {
    private final PromotionCommandService promotionCommandService;
    private final PromotionQueryService promotionQueryService;
    private final MemberQueryService memberQueryService;

    @PostMapping("/{memberId}")     //TODO remove pathVariable and use @AuthUser
    public ApiResponseDto<Long> createPromotion(@PathVariable Long memberId,
                                                @RequestBody PromotionRequest promotionRequest) {
        Member member = memberQueryService.getByMemberId(memberId);
        return ApiResponseDto.onSuccess(promotionCommandService.createPromotion(member, promotionRequest));
    }

    @PutMapping("/{promotionId}/members/{memberId}")       //TODO remove memberId pathVariable
    public ApiResponseDto<Long> modifyPromotion(@PathVariable Long memberId,
                                                @PathVariable Long promotionId,
                                                @RequestBody PromotionRequest promotionRequest) {
        Member member = memberQueryService.getByMemberId(memberId);
        return ApiResponseDto.onSuccess(promotionCommandService.modifyPromotion(member, promotionId, promotionRequest));
    }

    @GetMapping("/{promotionId}")
    public ApiResponseDto<PromotionDetailDto> getPromotionById(@PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(
                PromotionConverter.toDetailDto(
                        promotionQueryService.getPromotionById(promotionId)
                )
        );
    }

    @GetMapping
    public ApiResponseDto<PromotionListDto> getPromotionList(@RequestParam(defaultValue = "0") int currentPage) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        return ApiResponseDto.onSuccess(
                PromotionConverter.toListDto(
                        promotionQueryService.getPaginationPromotion(pageable)
                )
        );
    }

    @DeleteMapping("/{promotionId}/members/{memberId}")
    public ApiResponseDto<Boolean> deletePromotion(@PathVariable Long memberId,
                                                   @PathVariable Long promotionId) {
        Member member = memberQueryService.getByMemberId(memberId);
        promotionCommandService.removePromotion(member, promotionId);
        return ApiResponseDto.onSuccess(true);
    }
}
