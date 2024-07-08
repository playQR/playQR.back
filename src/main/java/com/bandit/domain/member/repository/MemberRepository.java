package com.bandit.domain.member.repository;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.repository.querydsl.MemberQueryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberQueryRepository {
    Optional<Member> findByNickname(String nickname);

    Optional<Member> findByKakaoEmail(String kakaoEmail);

    Optional<Member> findByUsername(String username);

    boolean existsByName(String name);

    boolean existsByNickname(String nickname);

    boolean existsByUsername(String username);
}
