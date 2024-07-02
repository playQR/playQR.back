package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.TicketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class TicketCommandServiceImpl implements TicketCommandService {

    private final TicketRepository ticketRepository;
    private final GuestRepository guestRepository;

    @Override
    public Long createTicket(Long guestId, Member member, Date dueDate) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.GUEST_NOT_FOUND));

        Ticket ticket = Ticket.builder().dueDate(dueDate).guest(guest).build();

        guest.markTicketAsIssued();

        return ticketRepository.save(ticket).getId();
    }

    @Override
    public void deleteTicket(Long ticketId, Member member) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));

        Guest guest = ticket.getGuest();
        guest.markTicketAsNotIssued();

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
