package com.bandit.domain.like.service.like_music;

import com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionDetailDto;
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
    public void isLiked(PromotionDetailDto response, Member member) {
        response.getMusicList().forEach(musicResponse -> {
            musicResponse.getMusicLikeDto().setLiked(
                    likeMusicRepository.existsByMusicIdAndMember(musicResponse.getId(), member)
            );
        });
    }

    @Override
    public long countLike(Long musicId) {
        return likeMusicRepository.countByMusicId(musicId);
    }

    @Override
    public void countLike(PromotionDetailDto response) {
        response.getMusicList().forEach(musicResponse ->
            musicResponse.getMusicLikeDto().setCount(
                    likeMusicRepository.countByMusicId(musicResponse.getId())
            )
        );
    }
}
