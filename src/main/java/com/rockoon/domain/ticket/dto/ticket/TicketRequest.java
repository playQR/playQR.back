package com.rockoon.domain.ticket.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class TicketRequest {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketRegisterDto {
        private Long guestId;
        private Date dueDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketModifyDto {
        private Date dueDate;
    }
}
