package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Ticket;

import java.util.List;

public interface TicketService {

    // 티켓 생성 및 발급
    Ticket issueTicket(Long guestId);

    // 모든 티켓 조회
    List<Ticket> findAllTickets();

    // 티켓 ID로 티켓 조회
    Ticket findTicketById(Long id);

    // 게스트 ID로 티켓 조회
    Ticket findTicketByGuestId(Long guestId);

    // 티켓 삭제
    void deleteTicket(Long id);
}
