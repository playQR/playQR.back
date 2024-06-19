package com.rockoon.domain.ticket.service.guest;

import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.rockoon.domain.ticket.entity.QGuest.guest;

@RequiredArgsConstructor
@Service
@Transactional
public class GuestCommandServiceImpl implements GuestCommandService {

    private final GuestRepository guestRepository;
    private final PromotionRepository promotionRepository;
    private final MemberRepository memberRepository;

    @Override
    public Guest createGuest(Long promotionId, Member member, String name) {
        return null;
    }

    @Override
    public Guest updateGuest(Long guestId, Guest guestDetails) {
        return null;
    }

    @Override
    public void deleteGuest(Long id) {

    }
}
