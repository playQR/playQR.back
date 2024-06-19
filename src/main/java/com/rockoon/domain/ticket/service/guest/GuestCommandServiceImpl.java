package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.global.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rockoon.domain.ticket.entity.QGuest.guest;

@RequiredArgsConstructor
@Service
@Transactional
public class GuestCommandServiceImpl implements GuestCommandService {

    private final GuestRepository guestRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public Guest createGuest(Long promotionId, Member member, String name) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new ResourceNotFoundException("Promotion not found for id: " + promotionId));

        Guest guest = Guest.builder()
                .promotion(promotion)
                .member(member)
                .name(name)
                .ticketIssued(false)
                .entered(false)
                .build();

        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long guestId, Guest guestDetails) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found for id: " + guestId));

        guest.setName(guestDetails.getName());
        guest.setTicketIssued(guestDetails.getTicketIssued());
        guest.setEntered(guestDetails.getEntered());

        return guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found for id: " + id));

        guestRepository.delete(guest);
    }
}
