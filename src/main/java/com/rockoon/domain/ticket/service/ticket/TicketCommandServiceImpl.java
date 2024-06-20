package com.rockoon.domain.ticket.service.ticket;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.entity.Ticket;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.domain.ticket.repository.TicketRepository;
import com.rockoon.global.exception.ResourceNotFoundException;
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
    public Long issueTicket(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found for id: " + guestId));

        guest.setTicketIssued(true);
        guestRepository.save(guest);

        Ticket ticket = Ticket.builder()
                .guest(guest)
                .data("QR Code Data")
                .dueDate(new Date())
                .build();

        return ticketRepository.save(ticket).getId();
    }

    @Override
    public void deleteTicket(Long ticketId, Member member) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found for id: " + ticketId));

        ticketRepository.delete(ticket);
    }
}
