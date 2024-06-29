package com.rockoon.domain.ticket.dto.guest;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequest {
    private Long promotionId;
    private String guestname;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GuestModifyDto {
        private String guestname;
    }
}
