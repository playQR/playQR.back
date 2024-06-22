package com.rockoon.domain.like.service.music_like;

import com.rockoon.domain.member.entity.Member;

public interface LikeMusicCommandService {
    Long likeMusic(Long promotionMusicId, Member member);

    void unlikeMusic(Long promotionMusicId, Member member);
}
