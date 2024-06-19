package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.global.exception.ResourceNotFoundException;
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
                .orElseThrow(() -> new ResourceNotFoundException("Guest not found for id: " + guestId));
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
