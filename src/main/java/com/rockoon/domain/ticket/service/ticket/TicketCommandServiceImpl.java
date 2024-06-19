package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.member.entity.Member;

public class TicketCommandServiceImpl implements TicketCommandService {
    @Override
    public Long issueTicket(Long guestId, Member member) {
        return 0L;
    }

    @Override
    public void deleteTicket(Long ticketId, Member member) {

    }
}
