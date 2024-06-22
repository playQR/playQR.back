package com.rockoon.domain.like.service.music_like;

import com.rockoon.domain.like.entity.LikeMusic;
import com.rockoon.domain.like.repository.LikeMusicRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.LikeHandler;
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
