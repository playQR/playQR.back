package com.rockoon.domain.like.service.music_like;

import com.rockoon.domain.like.repository.LikeMusicRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.music.repository.PromotionMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LikeMusicQueryServiceImpl implements LikeMusicQueryService {

    private final PromotionMusicRepository promotionMusicRepository;
    private final LikeMusicRepository likeMusicRepository;
    @Override
    public boolean isLiked(Long promotionMusicId, Member member) {
        return likeMusicRepository.existsByPromotionMusicIdAndMember(promotionMusicId, member);
    }

    @Override
    public long countLike(Long promotionMusicId) {
        return likeMusicRepository.countByPromotionMusicId(promotionMusicId);
    }
}
