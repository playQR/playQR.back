package com.rockoon.domain.team.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.entity.TeamMember;
import com.rockoon.domain.team.repository.TeamMemberRepository;
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
    private final TeamMemberRepository teamMemberRepository;

    @Override
    public Long createTeam(Member member, TeamRequest teamRequest) {
        Team team = teamRepository.save(Team.of(member, teamRequest));
        teamMemberRepository.save(buildTeamMember(member, team));
        return team.getId();
    }


    @Override
    public Long modifyTeam(Member member, Long teamId, TeamRequest teamRequest) {
        return null;
    }

    @Override
    public void addMemberInTeam(Member member, Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("not found team"));
        teamMemberRepository.save(buildTeamMember(member, team));
    }

    @Override
    public void removeMemberInTeamByMyself(Member member, Long teamId) {

    }

    @Override
    public void removeTeam(Member member, Long teamId) {

    }

    private static TeamMember buildTeamMember(Member member, Team team) {
        TeamMember teamMember = TeamMember.builder()
                .team(team)
                .member(member)
                .build();
        return teamMember;
    }
}
