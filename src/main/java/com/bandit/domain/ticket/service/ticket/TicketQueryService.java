package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.ticket.entity.Ticket;

import java.util.List;

public interface TicketQueryService {

    Ticket findTicketById(Long ticketId);

    List<Ticket> findAllTickets();

//    Page<Ticket> findTicketsByGuestId(Long guestId, Pageable pageable);

    Ticket findTicketsByPromotionId(Long promotionId);
}
