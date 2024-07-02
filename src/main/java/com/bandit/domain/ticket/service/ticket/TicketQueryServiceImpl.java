package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.TicketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TicketQueryServiceImpl implements TicketQueryService {

    private final TicketRepository ticketRepository;

    @Override
    public Ticket findTicketById(Long ticketId) {
        return ticketRepository.findById(ticketId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));
    }

    @Override
    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public Page<Ticket> findTicketsByGuestId(Long guestId, Pageable pageable) {
        return ticketRepository.findByGuestId(guestId, pageable);
    }

    @Override
    public List<Ticket> findTicketsByPromotionId(Long promotionId) {
        return ticketRepository.findByPromotionId(promotionId);
    }
}
