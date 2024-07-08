package com.bandit.domain.like.service.like_music;

import com.bandit.domain.like.entity.LikeMusic;
import com.bandit.domain.like.repository.LikeMusicRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.music.entity.Music;
import com.bandit.domain.music.repository.MusicRepository;
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
    private final MusicRepository musicRepository;
    @Override
    public Long likeMusic(Long musicId, Member member) {
        Music music = musicRepository.findById(musicId)
                .orElseThrow(() -> new PromotionMusicHandler(ErrorStatus.PROMOTION_MUSIC_NOT_FOUND));
        LikeMusic saveLikeMusic = likeMusicRepository.save(LikeMusic.of(music, member));
        return saveLikeMusic.getId();
    }

    @Override
    public void unlikeMusic(Long musicId, Member member) {
        LikeMusic likeMusic = likeMusicRepository.findByMusicIdAndMember(musicId, member)
                .orElseThrow(() -> new LikeHandler(ErrorStatus.LIKE_NOT_FOUND));
        likeMusicRepository.delete(likeMusic);
    }
}
