package com.rockoon.domain.ticket.controller;

import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.service.guest.GuestCommandService;
import com.rockoon.global.annotation.auth.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    private GuestCommandService guestCommandService;

    @PostMapping
    public Guest createGuest(@AuthUser Member member, @RequestParam Long promotionId, @RequestParam String name) {
        return guestCommandService.createGuest(promotionId, member, name);
    }

    @PutMapping("/{guestId}")
    public Guest updateGuest(@PathVariable Long guestId, @RequestBody Guest guestDetails) {
        return guestCommandService.updateGuest(guestId, guestDetails);
    }

    @DeleteMapping("/{guestId}")
    public void deleteGuest(@PathVariable Long guestId) {
        guestCommandService.deleteGuest(guestId);
    }
}
