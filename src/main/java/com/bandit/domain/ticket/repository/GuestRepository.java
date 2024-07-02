package com.bandit.domain.ticket.repository;

import com.bandit.domain.ticket.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    List<Guest> findByPromotionId(Long promotionId);

    Page<Guest> findByPromotionId(Long promotionId, Pageable pageable);
}
