package com.bandit.domain.ticket.controller;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.ticket.TicketRequest.TicketRegisterDto;
import com.bandit.domain.ticket.dto.ticket.TicketResponse;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.service.ticket.TicketCommandService;
import com.bandit.domain.ticket.service.ticket.TicketQueryService;
import com.bandit.global.annotation.api.ApiErrorCodeExample;
import com.bandit.global.annotation.auth.AuthUser;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ticket API", description = "티켓 관련 API")
@ApiResponse(responseCode = "2000", description = "성공")
@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketApiController {

    private final TicketCommandService ticketCommandService;
    private final TicketQueryService ticketQueryService;

    @Operation(summary = "티켓 생성 🔑", description = "게스트 ID와 멤버 정보, 만료 날짜를 받아 새로운 티켓을 생성합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @PostMapping("{guestId}")
    public ApiResponseDto<Long> createTicket(
            @PathVariable Long guestId,
            @AuthUser Member member,
            @RequestBody TicketRegisterDto request) {
        Long ticketId = ticketCommandService.createTicket(guestId, member, request);
        return ApiResponseDto.onSuccess(ticketId);
    }

    @Operation(summary = "티켓 삭제 🔑", description = "티켓 ID와 멤버 정보를 받아 기존 티켓을 삭제합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @DeleteMapping("/{ticketId}")
    public ApiResponseDto<Void> deleteTicket(
            @PathVariable Long ticketId,
            @AuthUser Member member) {
        ticketCommandService.deleteTicket(ticketId, member);
        return ApiResponseDto.onSuccess(null);
    }

    @Operation(summary = "티켓 조회", description = "티켓 ID를 받아 해당 티켓의 정보를 조회합니다.")
    @ApiErrorCodeExample({ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/{ticketId}")
    public ApiResponseDto<TicketResponse> getTicketById(@PathVariable Long ticketId) {
        Ticket ticket = ticketQueryService.findTicketById(ticketId);
        TicketResponse ticketResponse = TicketResponse.builder().ticketId(ticket.getId()).guestId(ticket.getGuest().getId()).dueDate(ticket.getDueDate()).build();
        return ApiResponseDto.onSuccess(ticketResponse);
    }


    @Operation(summary = "모든 티켓 조회", description = "모든 티켓의 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping
    public ApiResponseDto<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketQueryService.findAllTickets();
        return ApiResponseDto.onSuccess(tickets);
    }

    @Operation(summary = "게스트 ID로 티켓 페이징 조회", description = "게스트 ID를 받아 해당 게스트에 속한 티켓의 정보를 페이지별로 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/guest/{guestId}")
    public  ApiResponseDto<Page<Ticket>> getTicketsByGuestId(
            @PathVariable Long guestId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Ticket> ticketPage = ticketQueryService.findTicketsByGuestId(guestId, pageable);
        return ApiResponseDto.onSuccess(ticketPage);
    }

    @Operation(summary = "프로모션 ID로 티켓 조회", description = "프로모션 ID를 받아 해당 프로모션에 속한 티켓의 정보를 조회합니다.")
    @ApiErrorCodeExample(
            {ErrorStatus._INTERNAL_SERVER_ERROR})
    @GetMapping("/promotion/{promotionId}")
    public ApiResponseDto<List<Ticket>> getTicketsByPromotionId(@PathVariable Long promotionId) {
        List<Ticket> tickets = ticketQueryService.findTicketsByPromotionId(promotionId);
        return ApiResponseDto.onSuccess(tickets);
    }
}
