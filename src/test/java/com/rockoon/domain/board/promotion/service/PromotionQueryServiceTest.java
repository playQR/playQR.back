package com.rockoon.domain.board.promotion.service;

import com.rockoon.domain.board.promotion.entity.Promotion;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.entity.Role;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.team.service.TeamCommandService;
import com.rockoon.global.test.DatabaseCleanUp;
import com.rockoon.web.dto.promotion.PromotionRequest;
import com.rockoon.web.dto.team.TeamRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class PromotionQueryServiceTest {
    //service
    @Autowired
    PromotionQueryService promotionQueryService;
    @Autowired
    PromotionCommandService promotionCommandService;
    @Autowired
    TeamCommandService teamCommandService;
    //repository
    @Autowired
    MemberRepository memberRepository;
    //else(bean)
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //entity & Dto
    Member member1;
    Member member2;
    Member member3;
    TeamRequest teamRequest;


    @BeforeEach
    void setUp() {
        member1 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao@naver.com")
                .username("hann")
                .position("guitar")
                .nickname("hann")
                .build();
        member2 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao1@naver.com")
                .username("Hann")
                .position("piano")
                .nickname("Hann")
                .build();
        member3 = Member.builder()
                .role(Role.USER)
                .name("이정한")
                .profileImg("img")
                .kakaoEmail("kakao2@naver.com")
                .username("Hannw")
                .position("sing")
                .nickname("HHH")
                .build();
        memberRepository.saveAll(List.of(member1, member2, member3));
        teamRequest = TeamRequest.builder()
                .teamName("teamName")
                .password("12345")
                .build();
        Long teamId = teamCommandService.createTeam(member1, teamRequest);
        teamCommandService.addMemberInTeam(member2, teamId);
        Long anotherTeamId = teamCommandService.createTeam(member3, teamRequest);
        savePromotion(5, member1, teamId);
        savePromotion(5, member2, teamId);
        savePromotion(5, member3, anotherTeamId);
    }

    private void savePromotion(int count, Member member, Long teamId) {
        for (int i = 0; i < count; i++) {
            promotionCommandService.savePromotion(member, teamId,
                    PromotionRequest.builder()
                            .title("title" + i)
                            .content("content")
                            .build()
            );
        }
    }

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("작성된 모든 promotion을 가져옵니다")
    void getAllPromotion() {
        //given

        //when
        List<Promotion> all = promotionQueryService.getAll();
        //then
        assertThat(all)
                .hasSize(15);
    }
}