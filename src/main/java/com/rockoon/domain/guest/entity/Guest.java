package com.rockoon.domain.guest.entity;

import com.rockoon.domain.board.promotion.Promotion;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@SuperBuilder
public class Guest {
    @Id
    @GeneratedValue
    @Column(name = "guest_id")
    private Long id;

    private String name;

    private int participantsCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "promotion_id")
    private Promotion promotion;
}
