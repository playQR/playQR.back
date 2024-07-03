package com.bandit.domain.ticket.service.ticket;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.TicketHandler;
import lombok.RequiredArgsConstructor;
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
    public Ticket findTicketsByPromotionId(Long promotionId, Member member) {
        Ticket ticket = ticketRepository.findByPromotionId(promotionId)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));
        validateHost(ticket, member);
        return ticket;
    }

    private void validateHost(Ticket ticket, Member host) {
        if (!ticket.getPromotion().getWriter().equals(host)) {
            throw new TicketHandler(ErrorStatus.TICKET_ONLY_CAN_BE_OPENED_BY_MANAGERS)
        }
    }
}
