package com.rockoon.domain.board.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockoon.domain.board.dto.promotion.PromotionRequest;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.service.promotion.PromotionCommandService;
import com.rockoon.domain.board.service.promotion.PromotionQueryService;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.advice.ExceptionAdvice;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.PromotionHandler;
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
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

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

    @Test
    @DisplayName("유저가 작성한 게시글을 수정합니다.")
    void modifyPromotion() throws Exception{
        //given
        PromotionRequest request = PromotionRequest.builder().build();
        Long modifiedPromotionId = 4L;
        Long memberId = 3L;

        when(promotionCommandService.modifyPromotion(any(), any(), any())).thenReturn(modifiedPromotionId);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/promotions/" + modifiedPromotionId + "/members/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));
        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result").value(modifiedPromotionId));
    }

    @Test
    @DisplayName("프로모션을 수정할 때, 작성자가 아닌 유저가 프로모션을 수정하려는 접근을 제한합니다.")
    void executeExceptionWhenNotWriterTouchPromotion() throws Exception{
        //given
        PromotionRequest request = PromotionRequest.builder().build();
        Long modifiedPromotionId = 4L;
        Long memberId = 3L;
        when(promotionCommandService.modifyPromotion(any(), any(), any()))
                .thenThrow(new PromotionHandler(ErrorStatus.PROMOTION_ONLY_CAN_BE_TOUCHED_BY_WRITER));
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/promotions/" + modifiedPromotionId + "/members/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.message").value("작성자가 아닌 유저는 프로모션을 수정이 불가합니다."));
    }
    @Test
    @DisplayName("프로모션을 수정할 때, 존재하지 않는 게시글일 경우 동작이 실패합니다.")
    void executeExceptionWhenPromotionCannotBeFound() throws Exception{
        //given
        PromotionRequest request = PromotionRequest.builder().build();
        Long modifiedPromotionId = 4L;
        Long memberId = 3L;
        when(promotionCommandService.modifyPromotion(any(), any(), any()))
                .thenThrow(new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/promotions/" + modifiedPromotionId + "/members/" + memberId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.message").value(ErrorStatus.PROMOTION_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$.code").value(ErrorStatus.PROMOTION_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.isSuccess").value(false));
    }

    @Test
    @DisplayName("promotion id를 통해 원하는 promotion 정보를 가져옵니다.")
    void getPromotion() throws Exception{
        //given
        Long promotionId = 3L;
        Promotion promotion = Promotion.builder()
                .team("team")
                .content("content")
                .title("title")
                .maxAudience(3)
                .writer(Member.builder()
                        .id(4L)
                        .name("name")
                        .nickname("nickname")
                        .profileImg("profileImg")
                        .build())
                .build();
        when(promotionQueryService.getPromotionById(promotionId)).thenReturn(promotion);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/promotions/" + promotionId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.result.team").value(promotion.getTeam()))
                .andExpect(jsonPath("$.result.content").value(promotion.getContent()))
                .andExpect(jsonPath("$.result.title").value(promotion.getTitle()))
                .andExpect(jsonPath("$.result.maxAudience").value(promotion.getMaxAudience()));

        perform.andExpect(jsonPath("$.result.writer.name").value(promotion.getWriter().getName()))
                .andExpect(jsonPath("$.result.writer.nickname").value(promotion.getWriter().getNickname()))
                .andExpect(jsonPath("$.result.writer.profileImg").value(promotion.getWriter().getProfileImg()));
    }

    @Test
    @DisplayName("입력한 promotion id에 해당하는 정보가 존재하지 않아 조회에 실패합니다.")
    void executeExceptionWhenNotFoundPromotion() throws Exception{
        //given
        Long promotionId = 3L;
        when(promotionQueryService.getPromotionById(promotionId))
                .thenThrow(new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/promotions/" + promotionId)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.message").value(ErrorStatus.PROMOTION_NOT_FOUND.getMessage()))
                .andExpect(jsonPath("$.code").value(ErrorStatus.PROMOTION_NOT_FOUND.getCode()))
                .andExpect(jsonPath("$.isSuccess").value(false));

    }

    @Test
    @DisplayName("페이징을 사용하여 5개의 게시글을 조회합니다.")
    void getPromotionList() throws Exception {
        //given
        List<Promotion> promotionList = List.of(Promotion.builder().id(6L).writer(Member.builder().build()).build(),
                Promotion.builder().id(1L).writer(Member.builder().build()).build(),
                Promotion.builder().id(2L).writer(Member.builder().build()).build(),
                Promotion.builder().id(3L).writer(Member.builder().build()).build(),
                Promotion.builder().id(4L).writer(Member.builder().build()).build(),
                Promotion.builder().id(5L).writer(Member.builder().build()).build()
        );
        PageRequest pageRequest = PageRequest.of(0, 5);
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), promotionList.size());
        PageImpl<Promotion> promotions = new PageImpl<>(promotionList.subList(start, end), pageRequest, promotionList.size());
        for (Promotion promotion : promotions.getContent()) {
            log.info("promotion id = {}", promotion.getId());
        }

        when(promotionQueryService.getPaginationPromotion(any())).thenReturn(promotions);
        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/promotions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("currentPage", "0"));

        //then
        perform.andDo(print())
                .andExpect(jsonPath("$.isSuccess").value(true))
                .andExpect(jsonPath("$.result.promotionList.size()").value(5));

    }


}