package com.bandit.domain.like.repository;

import com.bandit.domain.like.entity.LikeMusic;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeMusicRepository extends JpaRepository<LikeMusic, Long> {
    Optional<LikeMusic> findByPromotionMusicIdAndMember(Long promotionMusicId, Member member);

    boolean existsByPromotionMusicIdAndMember(Long promotionMusicId, Member member);

    long countByPromotionMusicId(Long promotionMusicId);
}
