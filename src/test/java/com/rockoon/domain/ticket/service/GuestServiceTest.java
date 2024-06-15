package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.global.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class GuestServiceTest {

    @InjectMocks
    private GuestService guestService;

    @Mock
    private GuestRepository guestRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUpdateGuestName() {
        Guest guest = new Guest();
        guest.setGuestId(1L);
        guest.setName("Original Name");
        guest.setTicketIssued(false);
        guest.setEntered(false);

        Guest updatedGuest = new Guest();
        updatedGuest.setGuestId(1L);
        updatedGuest.setName("Updated Name");
        updatedGuest.setTicketIssued(false);
        updatedGuest.setEntered(false);

        when(guestRepository.findById(anyLong())).thenReturn(Optional.of(guest));
        when(guestRepository.save(any(Guest.class))).thenReturn(updatedGuest);

        Guest result = guestService.updateGuest(1L, updatedGuest);

        assertEquals("Updated Name", result.getName());
        verify(guestRepository, times(1)).findById(anyLong());
        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    public void testUpdateGuestNotFound() {
        Guest updatedGuest = new Guest();
        updatedGuest.setGuestId(1L);
        updatedGuest.setName("Updated Name");
        updatedGuest.setTicketIssued(false);
        updatedGuest.setEntered(false);

        when(guestRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            guestService.updateGuest(1L, updatedGuest);
        });

        verify(guestRepository, times(1)).findById(anyLong());
        verify(guestRepository, times(0)).save(any(Guest.class));
    }
}
