package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import java.util.List;
import java.util.Optional;

public interface GuestService {

    // 게스트 생성
    Guest createGuest(Long promotionId, Long memberId, String name);

    // 모든 게스트 조회
    List<Guest> findAllGuests();

    // 게스트 ID로 게스트 조회
    Optional<Guest> findGuestById(Long id);

    // 프로모션 ID로 게스트 목록 조회
    List<Guest> findGuestsByPromotionId(Long promotionId);

    // 게스트 삭제
    void deleteGuest(Long id);

    // 티켓 발급
    Guest issueTicket(Long guestId);

    // 게스트 정보 수정
    Guest updateGuest(Long guestId, Guest guestDetails);
}
