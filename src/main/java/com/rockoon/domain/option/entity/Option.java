package com.rockoon.domain.option.entity;

import com.rockoon.domain.board.entity.Board;
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
public class Option {
    @Id
    @GeneratedValue
    @Column(name = "option_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;
}
