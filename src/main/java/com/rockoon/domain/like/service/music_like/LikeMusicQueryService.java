package com.rockoon.domain.like.service.music_like;

import com.rockoon.domain.member.entity.Member;

public interface LikeMusicQueryService {

    boolean isLiked(Long promotionMusicId, Member member);

    long countLike(Long promotionMusicId);
}
