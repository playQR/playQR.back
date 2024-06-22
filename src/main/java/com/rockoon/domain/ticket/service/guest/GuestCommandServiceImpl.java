package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.presentation.payload.code.ErrorStatus;
import com.rockoon.presentation.payload.exception.GuestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class GuestCommandServiceImpl implements GuestCommandService {

    private final GuestRepository guestRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public Long createGuest(Long promotionId, Member member, String name) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.PROMOTION_NOT_FOUND));

        Guest guest = Guest.builder().promotion(promotion).member(member).name(name).ticketIssued(false).entered(false).build();

        return guestRepository.save(guest).getId();
    }

    @Override
    public void updateGuest(Long guestId, Guest guestDetails) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));

        validateCreator(guest.getMember(), guestDetails.getMember());

        guest.updateEntryStatus(guestDetails.getEntered());
        guest.markTicketAsIssued(guestDetails.getTicketIssued());
        guest.updateName(guestDetails.getName());

        guestRepository.save(guest);
    }

    @Override
    public void deleteGuest(Long id) {
        Guest guest = guestRepository.findById(id)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));

        guestRepository.delete(guest);
    }

    private void validateCreator(Member creator, Member currentUser) {
        if (!creator.equals(currentUser)) {
            throw new GuestHandler(ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR);
        }
    }
}