package com.bandit.domain.like.service.like_music;


import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;
import com.bandit.domain.member.entity.Member;

public interface LikeMusicQueryService {

    boolean isLiked(Long musicId, Member member);

    void isLiked(PromotionDetailDto response, Member member);

    long countLike(Long musicId);

    void countLike(PromotionDetailDto response);
}
