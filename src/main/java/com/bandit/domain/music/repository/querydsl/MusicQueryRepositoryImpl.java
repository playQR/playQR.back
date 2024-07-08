package com.bandit.domain.music.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bandit.domain.like.entity.QLikeMusic.likeMusic;
import static com.bandit.domain.music.entity.QMusic.music;

@RequiredArgsConstructor
@Repository
public class MusicQueryRepositoryImpl implements MusicQueryRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public void deleteWithRelations(Long promotionId) {
        queryFactory.delete(likeMusic).where(likeMusic.music.promotion.id.eq(promotionId)).execute();
        queryFactory.delete(music).where(music.promotion.id.eq(promotionId)).execute();
    }
}
