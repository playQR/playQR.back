package com.bandit.domain.ticket.converter;

import com.bandit.domain.ticket.dto.ticket.TicketResponse;
import com.bandit.domain.ticket.entity.Ticket;

public class TicketConverter {
    public static TicketResponse toResponse(Ticket ticket) {
        return TicketResponse.builder()
                .ticketId(ticket.getId())
                .uuid(ticket.getUuid())
                .dueDate(ticket.getDueDate())
                .build();
    }
}
