package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.member.entity.Member;

public interface TicketCommandService {

    Long createTicket(Long guestId, Member member);

    void deleteTicket(Long ticketId, Member member);
}
