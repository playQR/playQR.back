package com.rockoon.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MemberApiController.class)
class MemberApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberQueryService memberQueryService;
    @MockBean
    private MemberCommandService memberCommandService;
    @MockBean
    private MemberRepository memberRepository;
    @MockBean
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("유저가 멤버를 등록합니다.")
    void registerMember() throws Exception {
        //given
        MemberRegisterDto request = MemberRegisterDto.builder()
                .name("name")
                .kakaoEmail("kakaoEmail@naver.com")
                .nickname("nickname")
                .profileImg("profileImg")
                .build();
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    //TODO @WithMockUser 사용
    @Test
    @DisplayName("유저가 memberId를 통해 등록된 멤버 정보를 수정합니다")
    void modifyMemberInfo() throws Exception {
        //given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .name("name")
                .kakaoEmail("kakaoEmail@naver.com")
                .nickname("nickname")
                .profileImg("profileImg")
                .build();
        Long memberId = memberCommandService.registerMember(memberRegisterDto);
        MemberModifyDto request = MemberModifyDto.builder()
                .name("modify")
                .nickname("modifiedNickname")
                .profileImg("modifiedProfileImg")
                .build();
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/members/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("유저가 memberid를 통해 등록된 회원 정보를 가져옵니다.")
    void getMemberInfo() throws Exception {
        //given
        MemberRegisterDto request = MemberRegisterDto.builder()
                .name("name")
                .kakaoEmail("kakaoEmail@naver.com")
                .nickname("nickname")
                .profileImg("profileImg")
                .build();
        Long memberId = memberCommandService.registerMember(request);
        Member member = Member.builder()
                .id(memberId)
                .name(request.getName())
                .nickname(request.getNickname())
                .kakaoEmail(request.getKakaoEmail())
                .profileImg(request.getProfileImg())
                .build();
        when(memberQueryService.getByMemberId(any())).thenReturn(member);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(member.getName()));

    }

}