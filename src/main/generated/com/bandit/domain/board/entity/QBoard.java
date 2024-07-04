package com.bandit.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBoard is a Querydsl query type for Board
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBoard extends EntityPathBase<Board> {

    private static final long serialVersionUID = -1827359508L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBoard board = new QBoard("board");

    public final com.bandit.domain.auditing.entity.QBaseTimeEntity _super = new com.bandit.domain.auditing.entity.QBaseTimeEntity(this);

    public final ListPath<com.bandit.domain.image.entity.Image, com.bandit.domain.image.entity.QImage> boardImageList = this.<com.bandit.domain.image.entity.Image, com.bandit.domain.image.entity.QImage>createList("boardImageList", com.bandit.domain.image.entity.Image.class, com.bandit.domain.image.entity.QImage.class, PathInits.DIRECT2);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath title = createString("title");

    public final com.bandit.domain.member.entity.QMember writer;

    public QBoard(String variable) {
        this(Board.class, forVariable(variable), INITS);
    }

    public QBoard(Path<? extends Board> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBoard(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBoard(PathMetadata metadata, PathInits inits) {
        this(Board.class, metadata, inits);
    }

    public QBoard(Class<? extends Board> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new com.bandit.domain.member.entity.QMember(forProperty("writer")) : null;
    }

}

