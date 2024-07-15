package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.board.repository.PromotionRepository;
import com.bandit.domain.manager.repository.ManagerRepository;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestResponse.PromotionReservationDto;
import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.GuestHandler;
import com.bandit.presentation.payload.exception.PromotionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GuestQueryServiceImpl implements GuestQueryService{

    private final GuestRepository guestRepository;
    private final PromotionRepository promotionRepository;
    private final ManagerRepository managerRepository;

    @Override
    public Guest findGuestById(Long guestId, Member member) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
        validateHostOrManager(guest.getPromotion().getId(), member);
        return guest;
    }

    @Override
    public Guest findByPromotionAndMember(Promotion promotion, Member member) {
        return guestRepository.findByMemberAndPromotion(member, promotion)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
    }

    @Override
    public List<Guest> findGuestsByPromotionId(Long promotionId, Member member) {
        validateHostOrManager(promotionId, member);
        return guestRepository.findByPromotionId(promotionId);
    }


    @Override
    public Page<Guest> findGuestsByPromotionId(Long promotionId, Member member, Pageable pageable) {
        validateHostOrManager(promotionId, member);
        return guestRepository.findByPromotionId(promotionId, pageable);
    }

    @Override
    public PromotionReservationDto getReservationInfo(Long promotionId) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        return builderReservationDto(promotionId, promotion);
    }

    @Override
    public Page<Guest> findGuestByMember(Member member, PageRequest pageable) {
        return guestRepository.findAllByMember(member, pageable);
    }

    @Override
    public Boolean checkReservation(Long promotionId, Member member) {
        return guestRepository.existsByPromotionIdAndMember(promotionId, member);
    }

    private PromotionReservationDto builderReservationDto(Long promotionId, Promotion promotion) {
        return PromotionReservationDto.builder()
                .currentCount(guestRepository.findTotalReservationCountByPromotionId(promotionId))
                .maxAudience(promotion.getMaxAudience())
                .promotionId(promotion.getId())
                .build();
    }

    private void validateHostOrManager(Long promotionId, Member member) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new PromotionHandler(ErrorStatus.PROMOTION_NOT_FOUND));
        boolean isHost = promotion.getWriter().equals(member);
        boolean isManager = managerRepository.existsByPromotionAndMember(promotion, member);

        if (!isHost && !isManager) {
            throw new GuestHandler(ErrorStatus.GUEST_ONLY_CAN_BE_TOUCHED_BY_CREATOR);
        }
    }
}
