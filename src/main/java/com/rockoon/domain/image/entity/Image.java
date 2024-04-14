package com.rockoon.domain.image.entity;

import com.rockoon.domain.board.entity.Board;
import com.rockoon.web.dto.image.ImageRequest;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
//@SuperBuilder
public class Image {
    @Id
    @GeneratedValue
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
