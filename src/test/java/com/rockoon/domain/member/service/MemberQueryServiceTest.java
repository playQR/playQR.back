package com.rockoon.domain.member.service;

import com.rockoon.domain.member.dto.MemberRequest.MemberReigsterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.global.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MemberQueryServiceTest {
    //service
    @Autowired
    MemberQueryService memberQueryService;
    @Autowired
    MemberCommandService memberCommandService;
    //Else(Bean)
    @Autowired
    DatabaseCleanUp databaseCleanUp;
    //entity & dto & filed
    MemberReigsterDto build;
    Long memberId;

    @BeforeEach
    void setUp() {
        build = MemberReigsterDto.builder()
                .name("name")
                .nickname("nickname")
                .kakaoEmail("kakaoEmail")
                .position("position")
                .profileImg("profileImg")
                .build();
        memberId = memberCommandService.registerMember(build);
    }
    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("등록된 멤버를 pk를 통해 조회합니다")
    void readMemberById() {
        //when
        Member member = memberQueryService.getByMemberId(memberId);
        //then
        assertThat(member)
                .extracting("name", "nickname", "kakaoEmail", "position", "profileImg")
                .containsExactly(
                        build.getName(),
                        build.getNickname(),
                        build.getKakaoEmail(),
                        build.getPosition(),
                        build.getProfileImg()
                );
    }

}