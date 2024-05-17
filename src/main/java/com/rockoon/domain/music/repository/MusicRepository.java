package com.rockoon.domain.music.repository;

import com.rockoon.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {
    void deleteAllByPromotionId(Long promotionId);

    List<Music> findMusicsByPromotionId(Long promotionId);

    List<Music> findMusicsByTitleIn(List<String> titles);
}
