package com.rockoon.domain.option.entity;

import com.rockoon.domain.board.entity.Board;
import com.rockoon.web.dto.option.OptionRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
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

    public static Option of(Board board, OptionRequest request) {
        return Option.builder()
                .category(request.getCategory())
                .content(request.getContent())
                .board(board)
                .build();
    }
}
