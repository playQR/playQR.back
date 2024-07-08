package com.bandit.domain.like.repository;

import com.bandit.domain.like.entity.LikePromotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePromotionRepository extends JpaRepository<LikePromotion, Long> {
}
