package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketQueryService {

    List<Ticket> findAllTickets();

    Ticket findTicketById(Long ticketId);

    Ticket findTicketByGuestId(Long guestId);

    Page<Ticket> findTickets(Pageable pageable);
}
