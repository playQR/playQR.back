package com.rockoon.domain.ticket.repository;

import com.rockoon.domain.ticket.entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByPromotionId(Long promotionId);
}
