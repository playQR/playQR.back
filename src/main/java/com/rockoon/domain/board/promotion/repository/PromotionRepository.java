package com.rockoon.domain.board.promotion.repository;

import com.rockoon.domain.board.promotion.entity.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotionRepository extends JpaRepository<Promotion, Long> {
}
