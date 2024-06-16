package com.rockoon.domain.ticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.GuestService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.rockoon.global.config.test.DatabaseCleanUp;
import com.rockoon.presentation.advice.ExceptionAdvice;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = GuestController.class,
        includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = ExceptionAdvice.class))
@AutoConfigureMockMvc(addFilters = false)
class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GuestService guestService;

    @MockBean
    private MemberQueryService memberQueryService;

    @MockBean
    private DatabaseCleanUp databaseCleanUp;

    @AfterEach
    void cleanUp() {
        databaseCleanUp.truncateAllEntity();
    }

    @Test
    @DisplayName("유저 티켓 인원 수 및 성명 수정")
    void updateGuestDetails() throws Exception {
        //given
        Guest guest = new Guest();
        guest.setGuestId(1L);
        guest.setName("John Doe");
        guest.setTicketIssued(true);
        guest.setEntered(false);

        when(guestService.updateGuest(any(Long.class), any(Guest.class))).thenReturn(guest);

        String guestJson = objectMapper.writeValueAsString(guest);

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.put("/api/guests/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(guestJson));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketIssued").value(true))
                .andExpect(jsonPath("$.entered").value(false))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @DisplayName("프로모션 작성자 티켓 발급 허용")
    void issueTicket() throws Exception {
        //given
        Guest guest = new Guest();
        guest.setGuestId(1L);
        guest.setName("John Doe");
        guest.setTicketIssued(true);

        when(guestService.issueTicket(any(Long.class))).thenReturn(guest);

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.post("/api/guests/issue-ticket")
                .param("guestId", "1"));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketIssued").value(true));
    }

    @Test
    @DisplayName("작성자 티켓 신청 목록 조회")
    void getGuestsByPromotion() throws Exception {
        //given
        List<Guest> guests = List.of(new Guest(), new Guest(), new Guest());
        when(guestService.getGuestsByPromotion(any(Long.class))).thenReturn(guests);

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.get("/api/guests/promotions/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    @DisplayName("예매 신청 철회")
    void deleteGuest() throws Exception {
        //given
        // No specific setup needed as we're just testing the deletion

        //when
        ResultActions perform = mockMvc.perform(MockMvcRequestBuilders.delete("/api/guests/1"));

        //then
        perform.andDo(print())
                .andExpect(status().isNoContent());
    }
}
