package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Ticket;
import com.rockoon.domain.ticket.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;



}
