package com.rockoon.domain.team.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.entity.TeamMember;
import com.rockoon.domain.team.repository.TeamMemberRepository;
import com.rockoon.domain.team.repository.TeamRepository;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.domain.team.dto.TeamRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TeamCommandServiceTest {
    @Autowired
    TeamCommandService teamCommandService;
    //Repository
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DatabaseCleanUp databaseCleanUp;

    //entity & RequestDto
    Member member1;
    Member member2;

    @BeforeEach
    void setUp() {
        member1 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao@naver.com")
                .username("hann")
                .nickname("hann")
                .build();
        member2 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao1@naver.com")
                .username("Hann")
                .nickname("Hann")
                .build();
        memberRepository.saveAll(List.of(member1, member2));

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("회원이 팀을 생성합니다.")
    void createTeam() {
        //given
        TeamRequest request = TeamRequest.builder()
                .teamName("muje")
                .build();
        //when
        Long teamId = teamCommandService.createTeam(member1, request);
        //then
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        assertThat(optionalTeam).isPresent();
        assertThat(optionalTeam.get().getName()).isEqualTo(request.getTeamName());
    }
    @Test
    @DisplayName("생성된 팀에 멤버를 추가합니다.")
    void addMemberInTeam() {
        //given
        TeamRequest request = TeamRequest.builder()
                .teamName("muje")
                .build();
        Long teamId = teamCommandService.createTeam(member1, request);

        //when
        teamCommandService.addMemberInTeam(member2, teamId);
        //then
        List<TeamMember> allByTeamId = teamMemberRepository.findAllByTeamId(teamId);
        assertThat(allByTeamId).hasSize(2);

    }
}