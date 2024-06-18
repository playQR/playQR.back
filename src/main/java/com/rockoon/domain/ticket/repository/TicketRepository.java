package com.rockoon.domain.ticket.repository;

import com.rockoon.domain.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Guest ID로 티켓 조회
    Optional<Ticket> findByGuestId(Long guestId);
}
