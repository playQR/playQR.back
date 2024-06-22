package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Ticket;
import com.rockoon.domain.ticket.service.ticket.TicketCommandService;
import com.rockoon.domain.ticket.service.ticket.TicketQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketCommandService ticketCommandService;
    private final TicketQueryService ticketQueryService;

    @PostMapping
    public ResponseEntity<Long> createTicket(
            @RequestParam Long guestId,
            @RequestBody @Valid Member member,
            @RequestParam Date dueDate) {
        Long ticketId = ticketCommandService.createTicket(guestId, member, dueDate);
        return ResponseEntity.status(HttpStatus.CREATED).body(ticketId);
    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Void> deleteTicket(
            @PathVariable Long ticketId,
            @RequestBody @Valid Member member) {
        ticketCommandService.deleteTicket(ticketId, member);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable Long ticketId) {
        Ticket ticket = ticketQueryService.findTicketById(ticketId);
        return ResponseEntity.ok(ticket);
    }

    @GetMapping
    public ResponseEntity<List<Ticket>> getAllTickets() {
        List<Ticket> tickets = ticketQueryService.findAllTickets();
        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/guest/{guestId}")
    public ResponseEntity<Page<Ticket>> getTicketsByGuestId(
            @PathVariable Long guestId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Ticket> ticketPage = ticketQueryService.findTicketsByGuestId(guestId, pageable);
        return ResponseEntity.ok(ticketPage);
    }

    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity<List<Ticket>> getTicketsByPromotionId(@PathVariable Long promotionId) {
        List<Ticket> tickets = ticketQueryService.findTicketsByPromotionId(promotionId);
        return ResponseEntity.ok(tickets);
    }
}
