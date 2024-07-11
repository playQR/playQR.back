package com.bandit.domain.manager.repository;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.manager.entity.Manager;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByPromotionAndMember(Promotion promotion, Member member);
    boolean existsByPromotionAndMember(Promotion promotion, Member member);
    List<Manager> findByPromotion(Promotion promotion);
}

