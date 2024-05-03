package com.rockoon.domain.member.service;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.global.test.DatabaseCleanUp;
import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberReigsterDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class MemberCommandServiceTest {
    //service
    @Autowired
    MemberCommandService memberCommandService;
    //repository
    @Autowired
    MemberRepository memberRepository;
    //else
    @Autowired
    DatabaseCleanUp databaseCleanUp;


    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("멤버를 등록하여 데이터베이스의 내용과 일치하는지 확인합니다.")
    void registerMember() {
        //given
        MemberReigsterDto build = MemberReigsterDto.builder()
                .profileImg("profileImg")
                .name("name")
                .position("position")
                .nickname("nickname")
                .kakaoEmail("kakao")
                .build();
        //when
        Long memberId = memberCommandService.registerMember(build);
        //then
        Optional<Member> memberOptional = memberRepository.findById(memberId);
        assertThat(memberOptional).isPresent()
                .get()
                .extracting("nickname", "name", "profileImg", "kakaoEmail", "position")
                .containsExactly("nickname", "name", "profileImg", "kakao", "position");
    }
    @Test
    @DisplayName("member를 불러와 정보를 변경합니다.(kakaoEmail, username 제외)")
    void testMethodName() {
        //given
        MemberReigsterDto build = MemberReigsterDto.builder()
                .name("name")
                .nickname("nickname")
                .position("position")
                .profileImg("profileImg")
                .kakaoEmail("kakaoEmail")
                .build();
        Long memberId = memberCommandService.registerMember(build);
        MemberModifyDto modifyInfo = MemberModifyDto.builder()
                .name("modifiedName")
                .nickname("modifiedNickname")
                .position("modifiedPosition")
                .profileImg("modifiedProfileImg")
                .build();
        Member member = memberRepository.findById(memberId).get();
        //when
        memberCommandService.modifyMemberInfo(member, modifyInfo);

        //then
        assertThat(member)
                .extracting("name", "nickname", "position", "profileImg", "kakaoEmail")
                .containsExactly(
                        "modifiedName",
                        "modifiedNickname",
                        "modifiedPosition",
                        "modifiedProfileImg",
                        "kakaoEmail"
                        );
    }
}