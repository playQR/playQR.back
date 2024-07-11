package com.bandit.domain.ticket.dto.guest;

import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequest {

    private String name;
    private LocalDate depositDate;
    private int reservationCount;
}
