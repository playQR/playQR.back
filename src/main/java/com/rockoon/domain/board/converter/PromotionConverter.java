package com.rockoon.domain.board.converter;

import com.rockoon.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;
import com.rockoon.domain.board.dto.promotion.PromotionResponse.PromotionListDto;
import com.rockoon.domain.board.dto.promotion.PromotionResponse.PromotionSummaryDto;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.member.converter.MemberConverter;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class PromotionConverter {
    public static PromotionDetailDto toDetailDto(Promotion promotion) {
        return PromotionDetailDto.builder()
                .promotionId(promotion.getId())
                .title(promotion.getTitle())
                .team(promotion.getTeam())
                .content(promotion.getContent())
                .maxAudience(promotion.getMaxAudience())
                .writer(MemberConverter.toResponse(promotion.getWriter()))
                .build();
    }

    public static PromotionSummaryDto toSummaryDto(Promotion promotion) {
        return PromotionSummaryDto.builder()
                .promotionId(promotion.getId())
                .team(promotion.getTeam())
                .title(promotion.getTitle())
                .writer(MemberConverter.toResponse(promotion.getWriter()))
                .build();
    }

    public static PromotionListDto toListDto(Page<Promotion> paginationPromotion) {
        return PromotionListDto.builder()
                .promotionList(paginationPromotion.getContent().stream()
                        .map(PromotionConverter::toSummaryDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
