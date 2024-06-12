package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class GuestServiceTest {

    @Autowired
    private GuestService guestService;

    @Autowired
    private GuestRepository guestRepository;

    @Test
    public void testUpdateGuest() {
        Guest guest = new Guest();
        guest.setName("Original Name");
        guestRepository.save(guest);

        Guest updatedGuest = guestService.updateGuest(guest.getGuestId(), "Updated Name");

        assertEquals("Updated Name", updatedGuest.getName());
    }
}
