package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestRequest;

public interface GuestCommandService {

    Long createGuest(Long promotionId, Member member, String name);
    void updateGuest(Long guestId, Member member, GuestRequest.GuestModifyDto guestRequest);
    void deleteGuest(Long id);
}