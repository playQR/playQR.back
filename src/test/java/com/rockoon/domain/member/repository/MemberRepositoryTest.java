package com.rockoon.domain.member.repository;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.team.entity.Team;
import com.rockoon.domain.team.entity.TeamMember;
import com.rockoon.domain.team.repository.TeamMemberRepository;
import com.rockoon.domain.team.repository.TeamRepository;
import com.rockoon.global.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.groups.Tuple;
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
class MemberRepositoryTest {
    //repository
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    //Else(Bean)
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //entity & refactor & field
    Member member1;
    Member member2;

    Team team1;

    @BeforeEach
    void setUp() {
        member1 = Member.builder()
                .username("username1")
                .nickname("nickname1")
                .kakaoEmail("kakao1")
                .build();
        member2 = Member.builder()
                .username("username2")
                .nickname("nickname2")
                .kakaoEmail("kakao2")
                .build();
        memberRepository.saveAll(List.of(member1, member2));
        team1 = Team.builder()
                .leader(member1)
                .name("team1")
                .build();
        teamRepository.save(team1);
        TeamMember teamMember1 = TeamMember.builder()
                .member(member1)
                .team(team1)
                .build();
        TeamMember teamMember2 = TeamMember.builder()
                .member(member2)
                .team(team1)
                .build();
        teamMemberRepository.saveAll(List.of(teamMember1, teamMember2));

    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }
    @Test
    @DisplayName("닉네임을 통해 멤버 정보를 조회합니다.")
    void findByNickname() {
        //given
        Member member = Member.builder()
                .nickname("nickname")
                .name("name")
                .username("username")
                .profileImg("profileImg")
                .kakaoEmail("kakaoEmail")
                .position("position")
                .build();
        memberRepository.save(member);
        //when
        Optional<Member> memberOptional = memberRepository.findByNickname(member.getNickname());
        //then
        assertThat(memberOptional).isPresent()
                .get()
                .extracting(
                        "nickname", "name", "username", "profileImg", "kakaoEmail", "position"
                )
                .containsExactly(
                        member.getNickname(),
                        member.getName(),
                        member.getUsername(),
                        member.getProfileImg(),
                        member.getKakaoEmail(),
                        member.getPosition()
                );
    }
    @Test
    @DisplayName("teamId를 통해 멤버 리스트를 가져옵니다.")
    void findByTeamId() {
        //when
        List<Member> memberListByTeam = memberRepository.findByTeamId(team1.getId());
        //then
        assertThat(memberListByTeam)
                .hasSize(2)
                .extracting("username", "nickname", "kakaoEmail")
                .containsExactlyInAnyOrder(
                        Tuple.tuple(member1.getUsername(), member1.getNickname(), member1.getKakaoEmail()),
                        Tuple.tuple(member2.getUsername(), member2.getNickname(), member2.getKakaoEmail())
                );
    }

}