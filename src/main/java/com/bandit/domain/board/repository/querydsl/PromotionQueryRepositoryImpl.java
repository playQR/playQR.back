package com.bandit.domain.board.repository.querydsl;

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

import static com.bandit.domain.board.entity.QPromotion.promotion;
import static com.bandit.domain.ticket.entity.QGuest.guest;

@RequiredArgsConstructor
@Repository
public class PromotionQueryRepositoryImpl implements PromotionQueryRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<Promotion> searchPromotion(String keyword, Pageable pageable) {
        JPAQuery<Promotion> baseQuery = queryFactory.selectFrom(promotion);
        JPAQuery<Long> countQuery = queryFactory.select(promotion.count()).from(promotion);
        if (!(keyword == null || keyword.isEmpty())) {
            baseQuery.where(promotion.title.contains(keyword).or(promotion.team.contains(keyword)));
            countQuery.where(promotion.title.contains(keyword).or(promotion.team.contains(keyword)));
        }
        List<Promotion> promotionList = baseQuery
                .orderBy(promotion.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return PageableExecutionUtils.getPage(promotionList, pageable, countQuery::fetchCount);
    }

    @Override
    public Page<Promotion> findAsGuest(Member member, Pageable pageable) {
        JPAQuery<Promotion> baseQuery = queryFactory.select(guest.promotion).from(guest);
        JPAQuery<Long> countQuery = queryFactory.select(guest.promotion.count()).from(guest);
        List<Promotion> promotionList = baseQuery.orderBy(promotion.createdDate.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        return PageableExecutionUtils.getPage(promotionList, pageable, countQuery::fetchCount);
    }
}
