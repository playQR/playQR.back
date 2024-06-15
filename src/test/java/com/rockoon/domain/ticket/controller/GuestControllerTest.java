package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.GuestService;
import com.rockoon.domain.member.service.MemberQueryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GuestController.class)
public class GuestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestService guestService;

    @MockBean
    private MemberQueryService memberQueryService; // 모킹된 MemberQueryService 추가

    @Autowired
    private ObjectMapper objectMapper;  // JSON 직렬화를 위한 ObjectMapper

    @Test
    public void testUpdateGuestTicketIssuedStatus() throws Exception {
        Guest guest = new Guest();
        guest.setGuestId(1L);
        guest.setName("John Doe");
        guest.setTicketIssued(true);
        guest.setEntered(false);

        Mockito.when(guestService.updateGuest(Mockito.anyLong(), Mockito.any(Guest.class))).thenReturn(guest);

        String guestJson = objectMapper.writeValueAsString(guest);

        mockMvc.perform(put("/api/guests/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(guestJson))  // JSON 데이터를 문자열로 변환하여 전송
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ticketIssued").value(true))
                .andExpect(jsonPath("$.entered").value(false))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }
}
