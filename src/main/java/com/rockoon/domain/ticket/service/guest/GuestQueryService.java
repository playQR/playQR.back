package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.ticket.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuestQueryService {

    List<Guest> findAllGuests();

    Guest findGuestById(Long guestId);

    List<Guest> findGuestsByPromotionId(Long promotionId);

    Page<Guest> findGuestsByPromotionId(Long promotionId, Pageable pageable);
}
