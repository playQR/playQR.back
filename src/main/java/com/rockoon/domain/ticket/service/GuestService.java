package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.global.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    public Guest updateGuest(Long guestId, String name) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + guestId));
        guest.setName(name);
        return guestRepository.save(guest);
    }
}
