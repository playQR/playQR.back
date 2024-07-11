package com.bandit.domain.ticket.repository;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.querydsl.GuestQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long>, GuestQueryRepository {
    List<Guest> findByPromotionId(Long promotionId);

    Page<Guest> findByPromotionId(Long promotionId, Pageable pageable);

    Optional<Guest> findByMemberAndPromotion(Member member, Promotion promotion);

    void deleteByPromotionId(Long promotionId);

    boolean existsByPromotionIdAndMember(Long promotionId, Member member);
}
