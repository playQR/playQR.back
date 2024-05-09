package com.rockoon.domain.board.service.promotion;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.board.dto.promotion.PromotionRequest;

public interface PromotionCommandService {

    Long savePromotion(Member member, PromotionRequest request);

    Long modifyPromotion(Member member, Long promotionId, PromotionRequest request);

    void removePromotion(Member member, Long promotionId);
}
