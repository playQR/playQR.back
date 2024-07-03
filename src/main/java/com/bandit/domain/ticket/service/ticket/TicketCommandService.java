package com.bandit.domain.ticket.service.ticket;


import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.ticket.TicketRequest.TicketRegisterDto;

public interface TicketCommandService {

    Long createTicket(Long guestId, Member member, TicketRegisterDto request);

    void deleteTicket(Long ticketId, Member member);

    void entrance(String uuid);

}
