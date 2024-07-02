package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.ticket.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuestQueryService {

    List<Guest> findAllGuests();

    Guest findGuestById(Long guestId);

    List<Guest> findGuestsByPromotionId(Long promotionId);

    Page<Guest> findGuestsByPromotionId(Long promotionId, Pageable pageable);
}
