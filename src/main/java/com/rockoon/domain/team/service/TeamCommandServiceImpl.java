package com.rockoon.domain.team.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.repository.TeamRepository;
import com.rockoon.web.dto.team.TeamRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class TeamCommandServiceImpl implements TeamCommandService{

    private final TeamRepository teamRepository;

    @Override
    public Long createTeam(Member member, TeamRequest teamRequest) {
        Team team = teamRepository.save(Team.of(member, teamRequest));
        return team.getId();
    }

    @Override
    public Long modifyTeam(Member member, Long teamId, TeamRequest teamRequest) {
        return null;
    }

    @Override
    public void addMemberInTeam(Member member, Long teamId) {

    }

    @Override
    public void removeMemberInTeamByMyself(Member member, Long teamId) {

    }

    @Override
    public void removeTeam(Member member, Long teamId) {

    }
}
