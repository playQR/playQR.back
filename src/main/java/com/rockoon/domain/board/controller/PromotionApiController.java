package com.rockoon.domain.board.controller;

import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.board.service.promotion.PromotionQueryService;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.presentation.payload.dto.ApiResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/{promotionId}/member/{memberId}/")       //TODO remove memberId pathVariable
    public ApiResponseDto<Long> modifyPromotion(@PathVariable Long memberId,
                                                @PathVariable Long promotionId,
                                                @RequestBody PromotionRequest promotionRequest) {
        Member member = memberQueryService.getByMemberId(memberId);
        return ApiResponseDto.onSuccess(promotionCommandService.modifyPromotion(member, promotionId, promotionRequest));
    }

    @GetMapping("/{promotionId}")
    public ApiResponseDto<PromotionResponse.PromotionDetailDto> getPromotionById(@PathVariable Long promotionId) {
        return ApiResponseDto.onSuccess(promotionQueryService.getPromotionById(promotionId));
    }

    @GetMapping
    public ApiResponseDto<PromotionResponse.PromotionListDto> getPromotionList(
            @RequestParam(defaultValue = "0") int currentPage
    ) {
        Pageable pageable = PageRequest.of(currentPage, 5);
        PromotionConverter.toListDto(promotionQueryService.getPaginationPromotion(pageable));
        return ApiResponseDto.onSuccess();
    }
}
