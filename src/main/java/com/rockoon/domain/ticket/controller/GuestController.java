package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.guest.GuestCommandService;
import com.rockoon.domain.ticket.service.guest.GuestQueryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/guests")
@RequiredArgsConstructor
public class GuestController {

    private final GuestCommandService guestCommandService;
    private final GuestQueryService guestQueryService;

    @PostMapping
    public ResponseEntity<Long> createGuest(
            @RequestParam Long promotionId,
            @RequestBody @Valid Member member,
            @RequestParam String name) {
        Long guestId = guestCommandService.createGuest(promotionId, member, name);
        return ResponseEntity.status(HttpStatus.CREATED).body(guestId);
    }

    @PutMapping("/{guestId}")
    public ResponseEntity<Void> updateGuest(
            @PathVariable Long guestId,
            @RequestBody @Valid Guest guestDetails) {
        guestCommandService.updateGuest(guestId, guestDetails);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestCommandService.deleteGuest(guestId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<Guest>> getAllGuests() {
        List<Guest> guests = guestQueryService.findAllGuests();
        return ResponseEntity.ok(guests);
    }

    @GetMapping("/{guestId}")
    public ResponseEntity<Guest> getGuestById(@PathVariable Long guestId) {
        Guest guest = guestQueryService.findGuestById(guestId);
        return ResponseEntity.ok(guest);
    }

    @GetMapping("/promotion/{promotionId}")
    public ResponseEntity<List<Guest>> getGuestsByPromotionId(@PathVariable Long promotionId) {
        List<Guest> guests = guestQueryService.findGuestsByPromotionId(promotionId);
        return ResponseEntity.ok(guests);
    }

    @GetMapping("/promotion/{promotionId}/page")
    public ResponseEntity<Page<Guest>> getGuestsByPromotionIdPaged(
            @PathVariable Long promotionId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        Page<Guest> guestPage = guestQueryService.findGuestsByPromotionId(promotionId, pageable);
        return ResponseEntity.ok(guestPage);
    }
}
