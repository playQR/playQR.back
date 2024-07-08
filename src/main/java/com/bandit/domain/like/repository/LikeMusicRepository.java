package com.bandit.domain.like.repository;

import com.bandit.domain.like.entity.LikeMusic;
import com.bandit.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeMusicRepository extends JpaRepository<LikeMusic, Long> {
    Optional<LikeMusic> findByMusicIdAndMember(Long musicId, Member member);

    boolean existsByMusicIdAndMember(Long musicId, Member member);

    long countByMusicId(Long musicId);
}
