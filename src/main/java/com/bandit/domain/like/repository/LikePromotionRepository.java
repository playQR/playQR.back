package com.bandit.domain.like.repository;

import com.bandit.domain.like.entity.LikePromotion;
import com.bandit.domain.like.repository.querydsl.LikePromotionQueryRepository;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikePromotionRepository extends JpaRepository<LikePromotion, Long>, LikePromotionQueryRepository {
    void deleteByPromotionIdAndMember(Long promotionId, Member member);

    boolean existsByPromotionIdAndMember(Long promotionId, Member member);

    long countByPromotionId(Long promotionId);

    void deleteByPromotionId(Long promotionId);

}
