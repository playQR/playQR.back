package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestService guestService;

    @PutMapping("/{guestId}")
    public ResponseEntity<Guest> updateGuest(@PathVariable Long guestId, @RequestBody Guest guestDetails) {
        Guest updatedGuest = guestService.updateGuest(guestId, guestDetails.getName());
        return ResponseEntity.ok(updatedGuest);
    }
}
