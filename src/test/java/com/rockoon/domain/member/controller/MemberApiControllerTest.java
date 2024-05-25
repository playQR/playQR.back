package com.rockoon.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockoon.domain.member.dto.MemberRequest.MemberModifyDto;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.annotation.auth.AuthUser;
import com.rockoon.global.config.spring.WebMvcConfig;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.advice.ExceptionAdvice;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.MemberHandler;
import com.rockoon.security.config.SecurityConfig;
import com.rockoon.security.jwt.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = MemberApiController.class,
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ExceptionAdvice.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class),
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class),
                @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = AuthUser.class)
        })
class MemberApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private MemberQueryService memberQueryService;
    @MockBean
    private TokenService tokenService;
    @MockBean
    private MemberCommandService memberCommandService;
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
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2000))
                .andExpect(jsonPath("$.result").value(memberId));
    }

    //TODO @WithMockUser 사용
    @Test
    @DisplayName("유저가 memberId를 통해 등록된 멤버 정보를 수정합니다")
    void modifyMemberInfo() throws Exception {
        // given: 멤버 객체와 수정 DTO 준비
        Member member = Member.builder()
                .id(1L)
                .name("name")
                .build();
        MemberModifyDto request = MemberModifyDto.builder()
                .name("modify")
                .nickname("modifiedNickname")
                .profileImg("modifiedProfileImg")
                .build();

        // mocking behavior
        when(memberQueryService.getByMemberId(member.getId())).thenReturn(member);
        when(memberCommandService.modifyMemberInfo(member, request)).thenReturn(member.getId());

        // when: API 엔드포인트를 통한 멤버 정보 수정 요청
        ResultActions resultActions = mockMvc.perform(put("/api/members/" + member.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then: 응답 검증
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2000))
                .andExpect(jsonPath("$.result").value(member.getId()));
    }

    @Test
    @DisplayName("멤버 ID가 일치한 데이터가 존재하지 않을 경우 예외를 반환합니다.")
    void executeExceptionWhenGetMemberInfoById() throws Exception {
        //given
        Long nonMatchMemberId = 2L;
        when(memberQueryService.getByMemberId(nonMatchMemberId)).thenThrow(new MemberHandler(ErrorStatus.MEMBER_NOT_FOUND));
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/members/" + nonMatchMemberId)
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.name()));
        //then
        perform
                .andDo(print())
                .andExpect(jsonPath("$.code").value(ErrorStatus.MEMBER_NOT_FOUND.getCode()));
    }

    @Test
    @DisplayName("유저가 memberid를 통해 등록된 회원 정보를 가져옵니다.")
    void getMemberInfo() throws Exception {
        //given
        Long memberId = 3L;
        Member registerMember = Member.builder()
                .name("hi")
                .nickname("nickname")
                .profileImg("profileImg")
                .build();
        when(memberQueryService.getByMemberId(memberId)).thenReturn(registerMember);
        //when & then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/members/" + memberId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding(StandardCharsets.UTF_8))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.name").value(registerMember.getName()))
                .andExpect(jsonPath("$.result.nickname").value(registerMember.getNickname()))
                .andExpect(jsonPath("$.result.profileImg").value(registerMember.getProfileImg()));

    }

}