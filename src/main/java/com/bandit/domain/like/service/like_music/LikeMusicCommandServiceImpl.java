package com.bandit.domain.like.service.like_music;

import com.bandit.domain.like.entity.LikeMusic;
import com.bandit.domain.like.repository.LikeMusicRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.music.entity.PromotionMusic;
import com.bandit.domain.music.repository.PromotionMusicRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.LikeHandler;
import com.bandit.presentation.payload.exception.PromotionMusicHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeMusicCommandServiceImpl implements LikeMusicCommandService {

    private final LikeMusicRepository likeMusicRepository;
    private final PromotionMusicRepository promotionMusicRepository;
    @Override
    public Long likeMusic(Long promotionMusicId, Member member) {
        PromotionMusic promotionMusic = promotionMusicRepository.findById(promotionMusicId)
                .orElseThrow(() -> new PromotionMusicHandler(ErrorStatus.PROMOTION_MUSIC_NOT_FOUND));
        LikeMusic saveLikeMusic = likeMusicRepository.save(LikeMusic.of(promotionMusic, member));
        return saveLikeMusic.getId();
    }

    @Override
    public void unlikeMusic(Long promotionMusicId, Member member) {
        LikeMusic likeMusic = likeMusicRepository.findByPromotionMusicIdAndMember(promotionMusicId, member)
                .orElseThrow(() -> new LikeHandler(ErrorStatus.LIKE_NOT_FOUND));
        likeMusicRepository.delete(likeMusic);
    }
}
