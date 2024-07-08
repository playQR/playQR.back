package com.bandit.domain.music.repository;

import com.bandit.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    List<Music> findByPromotionId(Long promotionId);

    void deleteByPromotionId(Long promotionId);

//    List<Music> findMusicsByTitleIn(List<String> titles);
}
