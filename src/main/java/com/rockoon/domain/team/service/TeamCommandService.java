package com.rockoon.domain.team.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.web.dto.team.TeamRequest;

public interface TeamCommandService {

    Long createTeam(Member member, TeamRequest teamRequest);

    Long modifyTeam(Member member, Long teamId, TeamRequest teamRequest);

    void addMemberInTeam(Member member, Long teamId);

    void removeMemberInTeamByMyself(Member member, Long teamId);

    void removeTeam(Member leader, Long teamId);
}
