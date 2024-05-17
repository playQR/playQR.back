package com.rockoon.domain.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.board.service.promotion.PromotionQueryService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.advice.ExceptionAdvice;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(controllers = PromotionApiController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ExceptionAdvice.class))
class PromotionApiControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PromotionCommandService promotionCommandService;
    @MockBean
    private PromotionQueryService promotionQueryService;
    @MockBean
    private MemberQueryService memberQueryService;
    @MockBean
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("유저가 게시글을 작성합니다.")
    void createPromotion() throws Exception{
        //given
        PromotionRequest request = PromotionRequest.builder().build();
        Long memberId = 2L;
        Long promotionId = 1L;
        when(promotionCommandService.createPromotion(any(), any())).thenReturn(promotionId);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/promotions/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(promotionId));
    }


}