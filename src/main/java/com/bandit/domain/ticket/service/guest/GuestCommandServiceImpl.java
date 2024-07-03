package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.GuestHandler;
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
    public Long createGuest(Long promotionId, Member member, GuestRequest request) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.PROMOTION_NOT_FOUND));

        return guestRepository.save(Guest.of(promotion, member, request)).getId();
    }

    @Override
    public void updateGuest(Long guestId, Member member, GuestRequest request) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));

        validateCreator(guest.getMember(), member);

        guest.updateGuestDetails(request);
    }

    @Override
    public void deleteGuest(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
        validateCreator(guest.getMember(), member);

        guestRepository.delete(guest);
    }

    private void validateCreator(Member creator, Member currentUser) {
        if (!creator.equals(currentUser)) {
            throw new GuestHandler(ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR);
        }
    }
}