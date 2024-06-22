package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.member.entity.Member;

import java.util.Date;

public interface TicketCommandService {

    Long createTicket(Long guestId, Member member, Date dueDate);

    void deleteTicket(Long ticketId, Member member);
}
