package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.entity.Ticket;

import java.util.List;

public interface TicketQueryService {

    Ticket findTicketById(Long ticketId);

    List<Ticket> findAllTickets();


    Ticket findTicketsByPromotionId(Long promotionId, Member member);
}
