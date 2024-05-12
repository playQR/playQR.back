package com.rockoon.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockoon.domain.member.dto.MemberRequest.MemberRegisterDto;
import com.rockoon.domain.member.service.MemberCommandService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@Slf4j
@WebMvcTest(controllers = MemberApiController.class)
class MemberApiControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    MemberQueryService memberQueryService;
    @MockBean
    MemberCommandService memberCommandService;
    @MockBean
    DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("멤버를 등록하여 등록된 member의 Id를 requestBody에 리턴받는다.")
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
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber());

    }

}