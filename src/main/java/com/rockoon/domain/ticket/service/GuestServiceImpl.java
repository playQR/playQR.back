package com.rockoon.domain.ticket.service;

import com.rockoon.domain.ticket.entity.Guest;
import com.rockoon.domain.ticket.repository.GuestRepository;
import com.rockoon.domain.board.entity.Promotion;
import com.rockoon.domain.board.repository.PromotionRepository;
import com.rockoon.domain.member.entity.Member;
import com.rockoon.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class GuestServiceImpl implements GuestService {

    private final GuestRepository guestRepository;
    private final PromotionRepository promotionRepository;
    private final MemberRepository memberRepository;

    public GuestServiceImpl(GuestRepository guestRepository, PromotionRepository promotionRepository, MemberRepository memberRepository) {
        this.guestRepository = guestRepository;
        this.promotionRepository = promotionRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Guest createGuest(Long promotionId, Long memberId, String name) {
        // 프로모션 ID로 프로모션 정보 조회
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion ID"));

        // 회원 ID로 회원 정보 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        // 게스트 엔티티 생성 및 저장
        Guest guest = Guest.builder()
                .promotion(promotion)
                .member(member)
                .name(name)
                .ticketIssued(false)
                .entered(false)
                .build();

        return guestRepository.save(guest);
    }

    @Override
    public List<Guest> findAllGuests() {
        // 모든 게스트 조회
        return guestRepository.findAll();
    }

    @Override
    public Optional<Guest> findGuestById(Long id) {
        // 게스트 ID로 게스트 조회
        return guestRepository.findById(id);
    }

    @Override
    public List<Guest> findGuestsByPromotionId(Long promotionId) {
        // 프로모션 ID로 게스트 목록 조회
        return guestRepository.findByPromotionId(promotionId);
    }

    @Override
    public void deleteGuest(Long id) {
        // 게스트 삭제
        guestRepository.deleteById(id);
    }

    @Override
    public Guest issueTicket(Long guestId) {
        // 게스트 ID로 게스트 정보 조회
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));

        // 티켓 발급 상태 변경 및 저장
        guest.setTicketIssued(true);
        return guestRepository.save(guest);
    }

    @Override
    public Guest updateGuest(Long guestId, Guest guestDetails) {
        // 게스트 ID로 게스트 정보 조회
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));

        // 게스트 정보 업데이트
        guest.setName(guestDetails.getName());
        guest.setTicketIssued(guestDetails.getTicketIssued());
        guest.setEntered(guestDetails.getEntered());

        return guestRepository.save(guest);
    }
}
