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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        Long memberId = 5L;     // 개발자가 명시하는 변수
        when(memberCommandService.registerMember(request)).thenReturn(memberId);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2000))
                .andExpect(jsonPath("$.result").value(memberId));
    }

    //TODO @WithMockUser 사용
    @Test
    @DisplayName("유저가 memberId를 통해 등록된 멤버 정보를 수정합니다")
    void modifyMemberInfo() throws Exception {
        //given
        //1. register
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .name("name")
                .kakaoEmail("kakaoEmail@naver.com")
                .nickname("nickname")
                .profileImg("profileImg")
                .build();
        Long memberId = 1L;
        when(memberCommandService.registerMember(memberRegisterDto)).thenReturn(memberId);
        //2. get --> when jwt accepted, skip this line
        Member member = Member.builder().build();
        when(memberQueryService.getByMemberId(memberId)).thenReturn(member);
        //3. modify
        MemberModifyDto request = MemberModifyDto.builder()
                .name("modify")
                .nickname("modifiedNickname")
                .profileImg("modifiedProfileImg")
                .build();
        Long modifiedMemberId = memberId;
        when(memberCommandService.modifyMemberInfo(member, request)).thenReturn(modifiedMemberId);
        // when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/api/members/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2000))
                .andExpect(jsonPath("$.result").value(modifiedMemberId));
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
        Long memberId = 1L;
        when(memberCommandService.registerMember(request)).thenReturn(memberId);
        Member registerMember = Member.builder().name("hi").build();
        when(memberQueryService.getByMemberId(memberId)).thenReturn(registerMember);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.name").value(registerMember.getName()));

    }

}