package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketQueryService {

    Ticket findTicketById(Long ticketId);

    List<Ticket> findAllTickets();

    Page<Ticket> findTicketsByGuestId(Long guestId, Pageable pageable);

    List<Ticket> findTicketsByPromotionId(Long promotionId);
}
