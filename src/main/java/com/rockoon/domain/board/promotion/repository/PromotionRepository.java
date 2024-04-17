package com.rockoon.domain.board.promotion.repository;

import com.rockoon.domain.board.promotion.entity.Promotion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    Page<Promotion> findAll(Pageable pageable);

    List<Promotion> findPromotionsByOrderByCreatedDateDesc();

    List<Promotion> findPromotionsByTeamId(Long teamId);
}
