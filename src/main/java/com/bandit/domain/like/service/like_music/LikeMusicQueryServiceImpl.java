package com.bandit.domain.like.service.like_music;

import com.bandit.domain.like.repository.LikeMusicRepository;
import com.bandit.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeMusicQueryServiceImpl implements LikeMusicQueryService {

    private final LikeMusicRepository likeMusicRepository;
    @Override
    public boolean isLiked(Long musicId, Member member) {
        return likeMusicRepository.existsByMusicIdAndMember(musicId, member);
    }

    @Override
    public long countLike(Long musicId) {
        return likeMusicRepository.countByMusicId(musicId);
    }
}
