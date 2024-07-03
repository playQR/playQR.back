package com.bandit.domain.ticket.entity;

import com.bandit.domain.auditing.entity.BaseTimeEntity;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.ticket.dto.guest.GuestRequest;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    private String name;

    private int reservationCount;

    private LocalDate depositDate;

    @JsonProperty("entered")
    private Boolean isEntranced;

    public void updateName(String name) {
        this.name = name;
    }

    public void updateGuestDetails(GuestRequest guestRequest) {
       this.name = guestRequest.getName();
    }
}
