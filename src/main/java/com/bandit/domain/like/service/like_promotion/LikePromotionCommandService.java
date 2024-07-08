package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.member.entity.Member;

public interface LikePromotionCommandService {
    Long likePromotion(Long promotionId, Member member);

    void unlikePromotion(Long promotionId, Member member);
}
