package com.bandit.domain.like.service.like_music;


import com.bandit.domain.member.entity.Member;

public interface LikeMusicCommandService {
    Long likeMusic(Long musicId, Member member);

    void unlikeMusic(Long musicId, Member member);
}
