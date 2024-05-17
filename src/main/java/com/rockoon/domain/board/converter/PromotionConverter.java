package com.rockoon.domain.board.converter;

import com.rockoon.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.member.converter.MemberConverter;

public class PromotionConverter {
    public static PromotionDetailDto toDetailDto(Promotion promotion) {
        return PromotionDetailDto.builder()
                .promotionId(promotion.getId())
                .title(promotion.getTitle())
                .team(promotion.getTeam())
                .content(promotion.getContent())
                .writer(MemberConverter.toResponse(promotion.getWriter()))
                .build();
    }
}
