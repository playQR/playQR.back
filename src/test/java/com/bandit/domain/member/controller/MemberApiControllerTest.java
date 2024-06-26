package com.bandit.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.bandit.domain.member.dto.MemberRequest.MemberModifyDto;
import com.bandit.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.member.entity.Role;
import com.bandit.domain.member.service.MemberCommandService;
import com.bandit.domain.member.service.MemberQueryService;
import com.bandit.global.config.spring.WebMvcConfig;
import com.bandit.global.config.test.DatabaseCleanUp;
import com.bandit.global.util.ImageUtil;
import com.bandit.presentation.advice.ExceptionAdvice;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.MemberHandler;
import com.bandit.security.config.SecurityConfig;
import com.bandit.security.jwt.service.TokenService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
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
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = WebMvcConfig.class)
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
    @MockBean
    private ImageUtil imageUtil;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(ImageUtil.class, "SERVER_URI", "http://localhost:8080/");
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
        when(memberCommandService.registerMember(any())).thenReturn(memberId);
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

    @Test
    @WithMockUser(username = "username")
    @DisplayName("유저가 memberId를 통해 등록된 멤버 정보를 수정합니다")
    void modifyMemberInfo() throws Exception {
        // given
        Long memberId = 5L;
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberModifyDto request = MemberModifyDto.builder()
                .build();
        Member member = Member.builder()
                .id(memberId)
                .nickname("nickname")
                .name("name")
                .role(Role.USER)
                .username(username)
                .kakaoEmail("kakaoEmail")
                .build();
        when(memberQueryService.getMemberByUsername(username)).thenReturn(member);
        when(memberCommandService.modifyMemberInfo(member, request)).thenReturn(memberId);

        // when
        ResultActions resultActions = mockMvc.perform(put("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        resultActions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code").value(2000))
                .andExpect(jsonPath("$.result").value(memberId));
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