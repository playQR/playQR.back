package com.bandit.domain.ticket.entity;

import com.bandit.domain.auditing.entity.BaseTimeEntity;
import com.bandit.domain.board.entity.Promotion;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
@Table(name = "guest")
public class Guest extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guest_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id", nullable = false)
    private Promotion promotion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String name;

    private int reservationCount;

    private LocalDate depositDate;

    private Boolean isEntered;

    private Boolean isApproved;


    public static Guest of(Promotion promotion, Member member, GuestRequest request) {
        return Guest.builder()
                .promotion(promotion)
                .member(member)
                .isEntered(false)
                .isApproved(false)
                .name(request.getName())
                .depositDate(request.getDepositDate())
                .reservationCount(request.getReservationCount())
                .build();
    }

    public void updateGuestDetails(GuestRequest guestRequest) {
        this.reservationCount = guestRequest.getReservationCount();
        this.name = guestRequest.getName();
        this.depositDate = guestRequest.getDepositDate();
    }

    public void entrance() {
        this.isEntered = true;
    }

    public void approve() {
        this.isApproved = true;
    }
}
