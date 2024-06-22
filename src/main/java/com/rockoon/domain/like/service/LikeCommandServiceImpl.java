package com.rockoon.domain.like.service;

import com.rockoon.domain.like.entity.Like;
import com.rockoon.domain.like.repository.LikeRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.LikeHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeCommandServiceImpl implements LikeCommandService{

    private final LikeRepository likeRepository;
    @Override
    public Long likeMusic(Long promotionMusicId, Member member) {
        Like saveLike = likeRepository.save(Like.of(promotionMusicId, member));
        return saveLike.getId();
    }

    @Override
    public void unlikeMusic(Long promotionMusicId, Member member) {
        Like like = likeRepository.findByPromotionMusicIdAndMember(promotionMusicId, member)
                .orElseThrow(() -> new LikeHandler(ErrorStatus.LIKE_NOT_FOUND));
        likeRepository.delete(like);
    }
}
