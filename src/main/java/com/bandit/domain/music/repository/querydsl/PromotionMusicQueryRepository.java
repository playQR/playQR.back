package com.bandit.domain.music.repository.querydsl;

public interface PromotionMusicQueryRepository {
    void deleteMusicWithRelations(Long promotionId);
}
