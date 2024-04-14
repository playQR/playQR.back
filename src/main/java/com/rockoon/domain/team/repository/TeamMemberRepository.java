package com.rockoon.domain.team.repository;

import com.rockoon.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
