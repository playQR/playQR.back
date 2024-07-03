package com.bandit.domain.ticket.service.ticket;


import com.bandit.domain.member.entity.Member;

public interface TicketCommandService {

    void deleteTicket(Long ticketId, Member member);

    void entrance(String uuid);

}
