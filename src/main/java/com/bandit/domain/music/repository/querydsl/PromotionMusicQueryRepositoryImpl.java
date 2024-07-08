package com.bandit.domain.music.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.bandit.domain.like.entity.QLikeMusic.likeMusic;
import static com.bandit.domain.music.entity.QPromotionMusic.promotionMusic;

@RequiredArgsConstructor
@Repository
public class PromotionMusicQueryRepositoryImpl implements PromotionMusicQueryRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public void deleteMusicWithRelations(Long promotionId) {
        queryFactory.delete(likeMusic).where(likeMusic.promotionMusic.promotion.id.eq(promotionId)).execute();
        queryFactory.delete(promotionMusic).where(promotionMusic.promotion.id.eq(promotionId)).execute();
    }
}
