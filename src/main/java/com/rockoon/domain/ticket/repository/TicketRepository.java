package com.rockoon.domain.ticket.repository;

import com.rockoon.domain.ticket.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByUuid(UUID uuid);

    Optional<Ticket> findByGuestId(Long guestId);

    Page<Ticket> findByGuestId(Long guestId, Pageable pageable);

    List<Ticket> findByPromotionId(Long promotionId);
}
