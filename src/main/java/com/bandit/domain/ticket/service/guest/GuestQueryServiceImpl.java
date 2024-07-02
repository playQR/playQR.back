package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.ticket.entity.Guest;
import com.bandit.domain.ticket.repository.GuestRepository;
import com.bandit.presentation.payload.code.ErrorStatus;
import com.bandit.presentation.payload.exception.GuestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class GuestQueryServiceImpl implements GuestQueryService{

    private final GuestRepository guestRepository;

    @Override
    public List<Guest> findAllGuests() {
        return guestRepository.findAll();
    }

    @Override
    public Guest findGuestById(Long guestId) {
        return guestRepository.findById(guestId)
                .orElseThrow(() -> new GuestHandler(ErrorStatus.GUEST_NOT_FOUND));
    }

    @Override
    public List<Guest> findGuestsByPromotionId(Long promotionId) {
        return guestRepository.findByPromotionId(promotionId);
    }

    @Override
    public Page<Guest> findGuestsByPromotionId(Long promotionId, Pageable pageable) {
        return guestRepository.findByPromotionId(promotionId, pageable);
    }
}
