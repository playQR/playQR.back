package com.bandit.domain.ticket.service.ticket;


import com.bandit.domain.member.entity.Member;

import java.util.Date;

public interface TicketCommandService {

    Long createTicket(Long guestId, Member member, Date dueDate);

    void deleteTicket(Long ticketId, Member member);

    void entrance(String uuid);

}
