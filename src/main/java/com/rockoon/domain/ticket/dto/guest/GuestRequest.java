package com.rockoon.domain.ticket.dto.guest;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequest {

    private GuestCreateDto createDto;
    private GuestModifyDto modifyDto;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestCreateDto {
        private Long promotionId;
        private String name;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestModifyDto {
        private String name;
        private boolean entered;
        private boolean ticketIssued;
    }
}
