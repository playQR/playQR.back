package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.ticket.repository.PromotionRepository;
import com.rockoon.global.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    public Guest updateGuest(Long guestId, Guest guestDetails) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + guestId));

        guest.setName(guestDetails.getName());
        guest.setTicketIssued(guestDetails.getTicketIssued());
        guest.setEntered(guestDetails.getEntered());

        return guestRepository.save(guest);
    }

    public Guest issueTicket(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + guestId));

        guest.setTicketIssued(true);

        return guestRepository.save(guest);
    }

    public List<Guest> getGuestsByPromotion(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found with id " + promotionId));

        return guestRepository.findByPromotionId(promotionId);
    }

    public void deleteGuest(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found with id " + guestId));

        guestRepository.delete(guest);
    }
}
