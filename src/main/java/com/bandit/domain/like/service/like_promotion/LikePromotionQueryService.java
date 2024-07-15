package com.bandit.domain.like.service.like_promotion;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LikePromotionQueryService {

    boolean isLiked(Long promotionId, Member member);

    long countLike(Long promotionId);

    Page<Promotion> getMyFavoritePromotionList(Member member, Pageable pageable);
}
