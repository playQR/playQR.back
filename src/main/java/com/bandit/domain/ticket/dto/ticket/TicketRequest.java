package com.bandit.domain.ticket.dto.ticket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class TicketRequest {
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketRegisterDto {
        private LocalDate dueDate;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketModifyDto {
        private LocalDate dueDate;
    }
}
