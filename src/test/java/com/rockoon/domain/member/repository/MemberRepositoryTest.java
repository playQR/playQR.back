package com.rockoon.domain.member.repository;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.global.test.DatabaseCleanUp;
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
class MemberRepositoryTest {
    //repository
    @Autowired
    MemberRepository memberRepository;
    //Else(Bean)
    @Autowired
    DatabaseCleanUp databaseCleanUp;

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
                .build();
        memberRepository.save(member);
        //when
        Optional<Member> memberOptional = memberRepository.findByNickname(member.getNickname());
        //then
        assertThat(memberOptional).isPresent()
                .get()
                .extracting(
                        "nickname", "name", "username", "profileImg", "kakaoEmail"
                )
                .containsExactly(
                        member.getNickname(),
                        member.getName(),
                        member.getUsername(),
                        member.getProfileImg(),
                        member.getKakaoEmail()
                );
    }

}