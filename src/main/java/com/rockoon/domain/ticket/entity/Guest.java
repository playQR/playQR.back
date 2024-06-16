package com.rockoon.domain.ticket.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
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

    // Getter and Setter methods
    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(Long promotionId) {
        this.promotionId = promotionId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getTicketIssued() {
        return ticketIssued;
    }

    public void setTicketIssued(Boolean ticketIssued) {
        this.ticketIssued = ticketIssued;
    }

    public Boolean getEntered() {
        return entered;
    }

    public void setEntered(Boolean entered) {
        this.entered = entered;
    }
}
