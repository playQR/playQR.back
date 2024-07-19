package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.entity.Ticket;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.domain.ticket.repository.TicketRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.GuestHandler;
import com.bandit.presentation.payload.exception.TicketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional
public class GuestCommandServiceImpl implements GuestCommandService {

    private final GuestRepository guestRepository;
    private final TicketRepository ticketRepository;
    private final PromotionRepository promotionRepository;

    @Override
    public Long createGuest(Long promotionId, Member member, GuestRequest request) {
        validateAlreadyBooked(promotionId, member);
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        validateReservationCount(promotion, request.getReservationCount());
        return guestRepository.save(Guest.of(promotion, member, request)).getId();
    }

    @Override
    public void entrance(String uuid, Member member) {
        Ticket ticket = ticketRepository.findByUuid(uuid)
                .orElseThrow(() -> new TicketHandler(ErrorStatus.TICKET_NOT_FOUND));
        Guest guest = guestRepository.findByMemberAndPromotion(member, ticket.getPromotion())
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
        validateEntrance(guest);
        guest.entrance();
    }

    @Override
    public void approve(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
        validateCreator(guest.getPromotion().getWriter(), member);
        guest.approve();
    }

    @Override       //TODO remove this method
    public Long updateGuest(Long guestId, Member member, GuestRequest request) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
        validateCreator(guest.getMember(), member);

        guest.updateGuestDetails(request);
        return guest.getId();
    }

    @Override
    public void deleteGuestByHost(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
        validateHost(guest, member);

        guestRepository.delete(guest);
    }


    @Override
    public void deleteGuestByMyself(Long guestId, Member member) {
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

    private void validateReservationCount(Promotion promotion, int reservationCount) {
        if (promotion.getMaxAudience() <
                reservationCount + guestRepository.findTotalReservationCountByPromotionId(promotion.getId())) {
            throw new GuestHandler(ErrorStatus.GUEST_RESERVATION_EXCEEDS_THE_AVAILABLE_CAPACITY);
        }
    }

    private void validateAlreadyBooked(Long promotionId, Member member) {
        if (guestRepository.existsByPromotionIdAndMember(promotionId, member)) {
            throw new GuestHandler(ErrorStatus.GUEST_ALREADY_EXIST);
        }
    }

    private static void validateEntrance(Guest guest) {
        if (!guest.getIsApproved()) {
            throw new GuestHandler(ErrorStatus.GUEST_WAS_NOT_APPROVED);
        }
        if (guest.getIsEntered()) {
            throw new GuestHandler(ErrorStatus.GUEST_ALREADY_ENTRNACED);
        }
    }

    private void validateHost(Guest guest, Member member) {
        if (!guest.getPromotion().getWriter().equals(member)) {
            throw new GuestHandler(ErrorStatus.GUEST_NOT_AUTHORIZED_AS_HOST);
        }
    }
}