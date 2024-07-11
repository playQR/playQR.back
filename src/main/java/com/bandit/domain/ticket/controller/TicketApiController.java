package com.bandit.domain.ticket.controller;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.converter.TicketConverter;
import com.bandit.domain.ticket.dto.ticket.TicketResponse;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.service.ticket.TicketQueryService;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.bandit.global.annotation.api.PredefinedErrorStatus.AUTH;

@Tag(name = "Ticket API", description = "티켓 관련 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketApiController {

    private final TicketQueryService ticketQueryService;

    @Operation(summary = "티켓 조회 🔑", description = "티켓 ID를 받아 해당 티켓의 정보를 조회합니다.")
    @ApiErrorCodeExample(value = {
            ErrorStatus.TICKET_NOT_FOUND,
            ErrorStatus.TICKET_ONLY_CAN_BE_OPENED_BY_MANAGERS
    }, status = AUTH)
    @GetMapping("/promotions/{promotionId}")
    public ApiResponseDto<TicketResponse> getTicketById(@PathVariable Long promotionId,
                                                        @AuthUser Member member) {
        Ticket ticket = ticketQueryService.findTicketByPromotionId(promotionId, member);
        return ApiResponseDto.onSuccess(TicketConverter.toResponse(ticket));
    }

}
