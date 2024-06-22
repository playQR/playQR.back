package com.rockoon.domain.like.service;

import com.rockoon.domain.member.entity.Member;

public interface LikeCommandService {
    Long likeMusic(Long promotionMusicId, Member member);

    void unlikeMusic(Long promotionMusicId, Member member);
}
