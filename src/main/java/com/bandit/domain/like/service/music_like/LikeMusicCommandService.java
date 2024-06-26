package com.bandit.domain.like.service.music_like;


import com.bandit.domain.member.entity.Member;

public interface LikeMusicCommandService {
    Long likeMusic(Long promotionMusicId, Member member);

    void unlikeMusic(Long promotionMusicId, Member member);
}
