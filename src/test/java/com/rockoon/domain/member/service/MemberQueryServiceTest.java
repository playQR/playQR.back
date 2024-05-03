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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
    String notFoundNickname = "notFoundNickname";

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

    @Test
    @DisplayName("등록된 멤버를 nickname을 통해 조회합니다(nickname is unique value)")
    void readMemberByNickname() {
        //when
        Member memberByNickname = memberQueryService.getByNickname(build.getNickname());
        //then
        assertThat(memberByNickname)
                .extracting("name", "nickname", "kakaoEmail", "position", "profileImg")
                .containsExactly(
                        build.getName(),
                        build.getNickname(),
                        build.getKakaoEmail(),
                        build.getPosition(),
                        build.getProfileImg()
                );
    }
    @Test
    @DisplayName("멤버를 조회하는 메서드에서 [NOT_FOUND]상황에 발생하는 예외를 확인합니다.(byId, byNickname)")
    void checkExceptionWhenNotFound() {
        //when & then
        assertThatThrownBy(() -> memberQueryService.getByMemberId(0L))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("not found member");
        assertThatThrownBy(() -> memberQueryService.getByNickname(notFoundNickname))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("not found member");
    }

}