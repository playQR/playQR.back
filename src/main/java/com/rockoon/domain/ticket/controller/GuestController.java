package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PutMapping("/{guestId}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long guestId, @RequestBody Guest guestDetails) {
        Guest updatedGuest = guestService.updateGuest(guestId, guestDetails);
        return ResponseEntity.ok(updatedGuest);
    }

    @PostMapping("/issue-ticket")
    public ResponseEntity<Guest> issueTicket(@RequestParam Long guestId) {
        Guest updatedGuest = guestService.issueTicket(guestId);
        return ResponseEntity.ok(updatedGuest);
    }

    @GetMapping("/promotions/{promotionId}")
    public ResponseEntity<List<Guest>> getGuestsByPromotion(@PathVariable Long promotionId) {
        List<Guest> guests = guestService.getGuestsByPromotion(promotionId);
        return ResponseEntity.ok(guests);
    }

    @DeleteMapping("/{guestId}")
    public ResponseEntity<Void> deleteGuest(@PathVariable Long guestId) {
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }
}
