package com.bandit.domain.ticket.repository.querydsl;

import com.bandit.domain.ticket.entity.Guest;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bandit.domain.ticket.entity.QGuest.guest;

@RequiredArgsConstructor
@Repository
public class GuestQueryRepositoryImpl implements GuestQueryRepository{
    private final JPAQueryFactory queryFactory;

    @Override
    public Integer findTotalReservationCountByPromotionId(Long promotionId) {
        Integer result = queryFactory.select(guest.reservationCount.sum())
                .from(guest)
                .where(guest.promotion.id.eq(promotionId))
                .fetchOne();
        return result != null ? result : 0;
    }

    @Override
    public List<Guest> searchByName(Long boardId, String name) {
        JPAQuery<Guest> baseQuery = queryFactory.selectFrom(guest)
                .where(guest.promotion.id.eq(boardId));
        if (!(name == null || name.isEmpty())) {
            baseQuery.where(guest.name.contains(name));
        }
        return baseQuery.fetch();
    }
}
