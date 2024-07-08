package com.bandit.domain.like.service.like_music;


import com.bandit.domain.member.entity.Member;

public interface LikeMusicCommandService {
    Long likeMusic(Long promotionMusicId, Member member);

    void unlikeMusic(Long promotionMusicId, Member member);
}
