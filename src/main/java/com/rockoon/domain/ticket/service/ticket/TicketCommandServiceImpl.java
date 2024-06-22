package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.entity.Ticket;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.domain.ticket.repository.TicketRepository;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.TicketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

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
}
