package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.web.dto.promotion.PromotionRequest;

public interface PromotionCommandService {

    Long createPromotion(PromotionRequest request);

    Long updatePromotion(Long promotionId, Member member, PromotionRequest request);

    void deletePromotion(Long promotionId, Member member);
}
