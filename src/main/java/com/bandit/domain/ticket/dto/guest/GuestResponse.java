package com.bandit.domain.ticket.dto.guest;

import com.bandit.domain.member.dto.MemberResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class GuestResponse {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestViewDto {
        private Long guestId;
        private String name;
        private int reservationCount;
        private Boolean isEntranced;
        private LocalDate depositDate;
        private MemberResponse writer;
    }


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestListDto {
        private List<GuestViewDto> guestList;
    }
}
