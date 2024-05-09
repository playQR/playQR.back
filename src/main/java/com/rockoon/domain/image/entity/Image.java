package com.rockoon.domain.image.entity;

import com.rockoon.domain.board.entity.Board;
import com.rockoon.domain.auditing.entity.BaseTimeEntity;
import com.rockoon.domain.image.dto.ImageRequest;
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
public class Image extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;

    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Image of(Board board, ImageRequest request) {
        return Image.builder()
                .imageUrl(request.getImageUrl())
                .board(board)
                .build();
    }
}
