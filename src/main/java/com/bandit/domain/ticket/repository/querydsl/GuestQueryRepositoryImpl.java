package com.bandit.domain.ticket.repository.querydsl;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

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
}
