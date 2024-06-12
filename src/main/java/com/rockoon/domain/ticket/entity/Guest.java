package com.rockoon.domain.ticket.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Guest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long guestId;

    private Long promotionId;
    private Long memberId;
    private String name;

    @Column(name = "ticket_issued")
    @JsonProperty("ticketIssued")
    private Boolean ticketIssued;  // 필드 이름 변경

    @Column(name = "entered")
    @JsonProperty("entered")
    private Boolean entered;  // 필드 이름 변경
}
