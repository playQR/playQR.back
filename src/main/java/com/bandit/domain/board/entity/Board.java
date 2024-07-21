package com.bandit.domain.board.entity;

import com.bandit.domain.auditing.entity.BaseTimeEntity;
import com.bandit.domain.image.entity.Image;
import com.bandit.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "board")
public abstract class Board extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id")
    private Member writer;


    @Lob
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    protected String content;

    protected String title;

    @Builder.Default
    @OneToMany(mappedBy = "board")
    protected List<Image> boardImageList = new ArrayList<>();
}
