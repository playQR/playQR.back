package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.ticket.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class GuestQueryServiceImpl implements GuestQueryService{
    @Override
    public List<Guest> findAllGuests() {
        return List.of();
    }

    @Override
    public Guest findGuestById(Long guestId) {
        return null;
    }

    @Override
    public List<Guest> findGuestsByPromotionId(Long promotionId) {
        return List.of();
    }

    @Override
    public Page<Guest> findGuestsByPromotionId(Long promotionId, Pageable pageable) {
        return null;
    }
}
