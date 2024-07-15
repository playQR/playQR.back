package com.bandit.domain.like.repository.querydsl;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bandit.domain.like.entity.QLikePromotion.likePromotion;

@RequiredArgsConstructor
@Repository
public class LikePromotionQueryRepositoryImpl implements LikePromotionQueryRepository {
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Promotion> findPromotionByMember(Member member, Pageable pageable) {
        JPAQuery<Promotion> baseQuery = queryFactory.select(likePromotion.promotion).from(likePromotion);
        JPAQuery<Long> countQuery = queryFactory.select(likePromotion.promotion.count()).from(likePromotion);
        List<Promotion> promotionList = baseQuery.where(likePromotion.member.eq(member))
                .orderBy(likePromotion.promotion.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        countQuery.where(likePromotion.member.eq(member));
        return PageableExecutionUtils.getPage(promotionList, pageable, countQuery::fetchCount);
    }
}
