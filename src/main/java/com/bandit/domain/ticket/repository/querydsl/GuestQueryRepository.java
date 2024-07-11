package com.bandit.domain.ticket.repository.querydsl;

public interface GuestQueryRepository {

    Integer findTotalReservationCountByPromotionId(Long promotionId);
}
