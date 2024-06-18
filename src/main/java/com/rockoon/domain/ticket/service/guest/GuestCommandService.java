package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.member.entity.Member;

public interface GuestCommandService {

    // 게스트 생성
    Guest createGuest(Long promotionId, Member member, String name);

    // 게스트 정보 수정
    Guest updateGuest(Long guestId, Guest guestDetails);

    // 게스트 삭제
    void deleteGuest(Long id);
}