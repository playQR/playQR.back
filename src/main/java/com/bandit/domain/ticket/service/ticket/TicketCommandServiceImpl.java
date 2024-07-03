package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.ticket.TicketRequest.TicketRegisterDto;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.TicketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketCommandServiceImpl implements TicketCommandService {

    private final TicketRepository ticketRepository;

    @Override
    public void deleteTicket(Long ticketId, Member member) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));

        Guest guest = ticket.getGuest();

        ticketRepository.delete(ticket);
    }

    @Override
    public void entrance(String uuid) {
        Ticket ticket = ticketRepository.findByUuid(uuid)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));

        Guest guest = ticket.getGuest();
        guest.markAsEntered();

        ticketRepository.save(ticket);
    }
}
