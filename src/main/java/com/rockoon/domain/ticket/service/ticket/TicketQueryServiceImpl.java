package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class TicketQueryServiceImpl implements TicketQueryService {
    @Override
    public List<Ticket> findAllTickets() {
        return List.of();
    }

    @Override
    public Ticket findTicketById(Long ticketId) {
        return null;
    }

    @Override
    public Ticket findTicketByGuestId(Long guestId) {
        return null;
    }

    @Override
    public Page<Ticket> findTickets(Pageable pageable) {
        return null;
    }
}
