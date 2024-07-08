package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.member.entity.Member;

public interface LikePromotionQueryService {

    boolean isLiked(Long promotionId, Member member);

    long countLike(Long promotionId);
}
