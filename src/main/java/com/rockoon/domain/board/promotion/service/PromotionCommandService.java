package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.web.dto.promotion.PromotionRequest;

public interface PromotionCommandService {

    Long createPromotion(Member member, PromotionRequest request);

    Long updatePromotion(Member member, Long promotionId, PromotionRequest request);

    void deletePromotion(Member member, Long promotionId);
}
