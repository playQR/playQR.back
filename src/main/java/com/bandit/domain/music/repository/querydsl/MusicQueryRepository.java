package com.bandit.domain.music.repository.querydsl;

public interface MusicQueryRepository {

    void deleteWithRelations(Long promotionId);
}
