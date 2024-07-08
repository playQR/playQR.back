package com.bandit.domain.like.service.like_music;


import com.bandit.domain.member.entity.Member;

public interface LikeMusicQueryService {

    boolean isLiked(Long promotionMusicId, Member member);

    long countLike(Long promotionMusicId);
}
