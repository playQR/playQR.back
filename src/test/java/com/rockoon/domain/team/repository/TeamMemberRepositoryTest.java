package com.rockoon.domain.team.repository;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.entity.TeamMember;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class TeamMemberRepositoryTest {
    //repository
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    MemberRepository memberRepository;
    //Else(Bean)
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //entity & dto & field

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }
    @Transactional
    @Test
    @DisplayName("팀 아이디(PK)를 통해 팀 멤버를 조회합니다.")
    void findTeamMemberByTeamId() {
        //given
        Member member1 = Member.builder()
                .username("usernmae1")
                .nickname("nickname1")
                .kakaoEmail("kakaoEmail1")
                .build();
        Member member2 = Member.builder()
                .username("usernmae2")
                .nickname("nickname2")
                .kakaoEmail("kakaoEmail2")
                .build();
        memberRepository.saveAll(List.of(member1, member2));
        Team team = Team.builder()
                .name("team")
                .leader(member1)
                .build();
        teamRepository.save(team);
        teamMemberRepository.saveAll(List.of(
                TeamMember.builder().team(team).member(member1).build(),
                TeamMember.builder().team(team).member(member2).build()
        ));
        //when
        List<TeamMember> teamMemberList = teamMemberRepository.findAllByTeamId(team.getId());
        //then
        assertThat(teamMemberList).hasSize(2)
                .extracting("member", "team")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(member1, team),
                        Tuple.tuple(member2, team)
                );
    }

}