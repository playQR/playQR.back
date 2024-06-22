package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.presentation.payload.exception.TicketHandler;

import java.util.Date;

public interface TicketCommandService {

    Long createTicket(Long guestId, Member member, Date dueDate);

    void deleteTicket(Long ticketId, Member member);

    void enterByUUID(String uuid);

}
