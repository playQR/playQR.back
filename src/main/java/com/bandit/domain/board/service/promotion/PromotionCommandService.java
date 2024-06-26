package com.bandit.domain.board.service.promotion;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.board.dto.promotion.PromotionRequest;

public interface PromotionCommandService {

    Long createPromotion(Member member, PromotionRequest request);

    Long modifyPromotion(Member member, Long promotionId, PromotionRequest request);

    void removePromotion(Member member, Long promotionId);
}
