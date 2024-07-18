package com.bandit.domain.ticket.service.guest;

import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestResponse.PromotionReservationDto;
import com.bandit.domain.ticket.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GuestQueryService {

    Guest findGuestById(Long guestId, Member member);


    List<Guest> findGuestsByPromotionId(Long promotionId, Member member);

    Page<Guest> findGuestsByPromotionId(Long promotionId, Member member, Pageable pageable);

    PromotionReservationDto getReservationInfo(Long promotionId);

    Page<Guest> findGuestByMember(Member member, PageRequest pageable);

    Boolean checkReservation(Long promotionId, Member member);
}
