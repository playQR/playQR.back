package com.bandit.domain.music.repository;

import com.bandit.domain.music.entity.PromotionMusic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionMusicRepository extends JpaRepository<PromotionMusic, Long> {
    void deleteAllByPromotionId(Long promotionId);

    List<PromotionMusic> findMusicsByPromotionId(Long promotionId);
}
