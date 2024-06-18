package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.entity.Ticket;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.domain.ticket.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final GuestRepository guestRepository;

    @Override
    public Ticket issueTicket(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));

        guest.setTicketIssued(true);
        guestRepository.save(guest);

        Ticket ticket = Ticket.builder()
                .guest(guest)
                .data("QR Code Data")
                .dueDate(new Date())
                .build();

        return ticketRepository.save(ticket);
    }

    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ticket ID"));
    }

    @Override
    public Ticket findTicketByGuestId(Long guestId) {
        return ticketRepository.findByGuestId(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));
    }

    @Override
    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
