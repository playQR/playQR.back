package com.rockoon.domain.like.repository;

import com.rockoon.domain.like.entity.Like;
import com.rockoon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByPromotionMusicIdAndMember(Long promotionMusicId, Member member);
}
