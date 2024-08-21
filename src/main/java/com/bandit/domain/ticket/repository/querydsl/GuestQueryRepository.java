package com.bandit.domain.ticket.repository.querydsl;

import com.bandit.domain.ticket.entity.Guest;

import java.util.List;

public interface GuestQueryRepository {

    Integer findTotalReservationCountByPromotionId(Long promotionId);

    List<Guest> searchByName(Long boardId, String name);
}
