package com.bandit.domain.board.entity;

import com.bandit.domain.board.dto.promotion.PromotionRequest;
import com.bandit.domain.member.entity.Member;
import com.bandit.domain.music.entity.Music;
import com.bandit.domain.ticket.entity.Ticket;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@DiscriminatorValue("type_promotion")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "promotion")
public class Promotion extends Board {
    private int maxAudience;
    private int entranceFee;
    private String team;
    private LocalDate showDate;
    private String startTime;
    private String endTime;
    private String showLocation;
    private String bankName;
    private String account;
    private String accountHolder;
    private String refundInfo;

    @OneToOne(mappedBy = "promotion")
    private Ticket ticket;


    @Builder.Default
    @OneToMany(mappedBy = "promotion", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<Music> musicList = new ArrayList<>();

    public static Promotion of(Member member, PromotionRequest request) {
        return Promotion.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .maxAudience(request.getMaxAudience())
                .entranceFee(request.getEntranceFee())
                .team(request.getTeam())
                .writer(member)
                .showDate(request.getShowDate())
                .startTime(request.getStartTime())
                .endTime(request.getEndTime())
                .showLocation(request.getShowLocation())
                .bankName(request.getBankName())
                .account(request.getAccount())
                .accountHolder(request.getAccountHolder())
                .refundInfo(request.getRefundInfo())
                .build();
    }

    public void update(PromotionRequest updateRequest) {
        this.maxAudience = updateRequest.getMaxAudience();
        this.team = updateRequest.getTeam();
        this.title = updateRequest.getTitle();
        this.content = updateRequest.getContent();
        this.entranceFee = updateRequest.getEntranceFee();
        this.showDate = updateRequest.getShowDate();
        this.startTime = updateRequest.getStartTime();
        this.endTime = updateRequest.getEndTime();
        this.showLocation = updateRequest.getShowLocation();
        this.bankName = updateRequest.getBankName();
        this.account = updateRequest.getAccount();
        this.accountHolder = updateRequest.getAccountHolder();
        this.refundInfo = updateRequest.getRefundInfo();
    }
}
