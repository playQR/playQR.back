package com.bandit.domain.like.service.like_music;

import com.bandit.domain.like.entity.LikeMusic;
import com.bandit.domain.member.entity.Member;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.LikeHandler;
import com.bandit.domain.like.repository.LikeMusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeMusicCommandServiceImpl implements LikeMusicCommandService {

    private final LikeMusicRepository likeMusicRepository;
    @Override
    public Long likeMusic(Long promotionMusicId, Member member) {
        LikeMusic saveLikeMusic = likeMusicRepository.save(LikeMusic.of(promotionMusicId, member));
        return saveLikeMusic.getId();
    }

    @Override
    public void unlikeMusic(Long promotionMusicId, Member member) {
        LikeMusic likeMusic = likeMusicRepository.findByPromotionMusicIdAndMember(promotionMusicId, member)
                .orElseThrow(() -> new LikeHandler(ErrorStatus.LIKE_NOT_FOUND));
        likeMusicRepository.delete(likeMusic);
    }
}
