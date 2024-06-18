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
public class GuestService {

    private final GuestRepository guestRepository;
    private final PromotionRepository promotionRepository;
    private final MemberRepository memberRepository;

    public GuestService(GuestRepository guestRepository, PromotionRepository promotionRepository, MemberRepository memberRepository) {
        this.guestRepository = guestRepository;
        this.promotionRepository = promotionRepository;
        this.memberRepository = memberRepository;
    }

    public Guest createGuest(Long promotionId, Long memberId, String name) {
        Promotion promotion = promotionRepository.findById(promotionId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid promotion ID"));
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid member ID"));

        Guest guest = Guest.builder()
                .promotion(promotion)
                .member(member)
                .name(name)
                .ticketIssued(false)
                .entered(false)
                .build();

        return guestRepository.save(guest);
    }

    public List<Guest> findAllGuests() {
        return guestRepository.findAll();
    }

    public Optional<Guest> findGuestById(Long id) {
        return guestRepository.findById(id);
    }

    public List<Guest> findGuestsByPromotionId(Long promotionId) {
        return guestRepository.findByPromotionId(promotionId);
    }

    public void deleteGuest(Long id) {
        guestRepository.deleteById(id);
    }

    public Guest issueTicket(Long guestId) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));

        guest.setTicketIssued(true);
        return guestRepository.save(guest);
    }

    public Guest updateGuest(Long guestId, Guest guestDetails) {
        Guest guest = guestRepository.findById(guestId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid guest ID"));

        guest.setName(guestDetails.getName());
        guest.setTicketIssued(guestDetails.getTicketIssued());
        guest.setEntered(guestDetails.getEntered());

        return guestRepository.save(guest);
    }
}
