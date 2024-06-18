package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.member.entity.Member;

public interface GuestCommandService {

    Guest createGuest(Long promotionId, Member member, String name);

    Guest updateGuest(Long guestId, Guest guestDetails);

    void deleteGuest(Long id);
}