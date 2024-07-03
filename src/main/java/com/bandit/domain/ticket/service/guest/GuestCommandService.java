package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestRequest;

public interface GuestCommandService {

    Long createGuest(Long promotionId, Member member, GuestRequest request);

    boolean entrance(String uuid, Member member);
    Long updateGuest(Long guestId, Member member, GuestRequest request);
    void deleteGuest(Long guestId, Member member);
}