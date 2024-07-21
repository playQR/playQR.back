package com.bandit.domain.ticket.dto.guest;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GuestRequest {

    private String name;
    private LocalDate DepositDate;
    @Min(1)
    @Max(5)
    private int reservationCount;
}
