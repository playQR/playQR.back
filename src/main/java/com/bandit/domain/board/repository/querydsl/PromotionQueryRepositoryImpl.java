package com.bandit.domain.board.repository.querydsl;

import com.bandit.domain.board.dto.promotion.PromotionResponse.MyPromotionListDto;
import com.bandit.domain.board.dto.promotion.PromotionResponse.MyPromotionSummaryDto;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.entity.QPromotion;
import com.bandit.domain.member.dto.MemberResponse;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.entity.QMember;
import com.bandit.domain.ticket.dto.ticket.TicketResponse;
import com.bandit.domain.ticket.entity.QTicket;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bandit.domain.board.dto.promotion.PromotionResponse.PromotionSummaryDto;
import static com.bandit.domain.board.entity.QPromotion.promotion;

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
    public MyPromotionListDto getMyPromotionWithTicket(Member member, Pageable pageable) {
        QPromotion promotion = QPromotion.promotion;
        QTicket ticket = QTicket.ticket;
        QMember writer = QMember.member;
        List<MyPromotionSummaryDto> myPromotionList = queryFactory
                .select(Projections.constructor(MyPromotionSummaryDto.class,
                        Projections.constructor(PromotionSummaryDto.class,
                                promotion.id,
                                promotion.title,
                                promotion.team,
                                promotion.boardImageList.get(0),
                                promotion.showDate,
                                promotion.showLocation,
                                promotion.startTime,
                                promotion.endTime,
                                promotion.entranceFee,
                                Projections.constructor(MemberResponse.class,
                                        writer.id,
                                        writer.name,
                                        writer.kakaoEmail
                                )
                        ),
                        Projections.constructor(TicketResponse.class,
                                ticket.id,
                                ticket.uuid,
                                ticket.dueDate
                        )
                ))
                .from(promotion)
                .leftJoin(promotion.ticket, ticket)
                .leftJoin(promotion.writer, writer)
                .where(writer.eq(member))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .select(promotion.count())
                .from(promotion)
                .where(writer.eq(member))
                .fetchOne();

        return MyPromotionListDto.builder()
                .promotionList(myPromotionList)
                .totalCount(totalCount)
                .build();
    }
}
