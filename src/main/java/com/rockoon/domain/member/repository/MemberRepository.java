package com.rockoon.domain.member.repository;

import com.rockoon.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickname(String nickname);

    @Query("SELECT m FROM Member m JOIN TeamMember tm where tm.team.id = :teamId")
    List<Member> findByTeamId(@Param(value = "teamId") Long teamId);
}
