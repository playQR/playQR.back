package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.board.promotion.dto.PromotionRequest;

public interface PromotionCommandService {

    Long savePromotion(Member member, Long teamId, PromotionRequest request);

    Long modifyPromotion(Member member, Long promotionId, PromotionRequest request);

    void removePromotion(Member member, Long promotionId);
}
