package com.rockoon.domain.option.entity;

import com.rockoon.domain.board.entity.Board;
import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import com.rockoon.domain.option.dto.OptionRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@SuperBuilder
public class Option extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Option of(Board board, OptionRequest request) {
        return Option.builder()
                .category(request.getCategory())
                .content(request.getContent())
                .board(board)
                .build();
    }
}
