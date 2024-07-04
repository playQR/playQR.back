package com.bandit.domain.board.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotion is a Querydsl query type for Promotion
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotion extends EntityPathBase<Promotion> {

    private static final long serialVersionUID = 249188265L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotion promotion = new QPromotion("promotion");

    public final QBoard _super;

    public final StringPath account = createString("account");

    public final StringPath accountHolder = createString("accountHolder");

    public final StringPath bankName = createString("bankName");

    //inherited
    public final ListPath<com.bandit.domain.image.entity.Image, com.bandit.domain.image.entity.QImage> boardImageList;

    //inherited
    public final StringPath content;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate;

    public final StringPath endTime = createString("endTime");

    public final NumberPath<Integer> entranceFee = createNumber("entranceFee", Integer.class);

    //inherited
    public final NumberPath<Long> id;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate;

    public final NumberPath<Integer> maxAudience = createNumber("maxAudience", Integer.class);

    public final ListPath<com.bandit.domain.music.entity.PromotionMusic, com.bandit.domain.music.entity.QPromotionMusic> promotionMusicList = this.<com.bandit.domain.music.entity.PromotionMusic, com.bandit.domain.music.entity.QPromotionMusic>createList("promotionMusicList", com.bandit.domain.music.entity.PromotionMusic.class, com.bandit.domain.music.entity.QPromotionMusic.class, PathInits.DIRECT2);

    public final StringPath refundInfo = createString("refundInfo");

    public final DatePath<java.time.LocalDate> showDate = createDate("showDate", java.time.LocalDate.class);

    public final StringPath showLocation = createString("showLocation");

    public final StringPath startTime = createString("startTime");

    public final StringPath team = createString("team");

    public final com.bandit.domain.ticket.entity.QTicket ticket;

    //inherited
    public final StringPath title;

    // inherited
    public final com.bandit.domain.member.entity.QMember writer;

    public QPromotion(String variable) {
        this(Promotion.class, forVariable(variable), INITS);
    }

    public QPromotion(Path<? extends Promotion> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotion(PathMetadata metadata, PathInits inits) {
        this(Promotion.class, metadata, inits);
    }

    public QPromotion(Class<? extends Promotion> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this._super = new QBoard(type, metadata, inits);
        this.boardImageList = _super.boardImageList;
        this.content = _super.content;
        this.createdDate = _super.createdDate;
        this.id = _super.id;
        this.lastModifiedDate = _super.lastModifiedDate;
        this.ticket = inits.isInitialized("ticket") ? new com.bandit.domain.ticket.entity.QTicket(forProperty("ticket"), inits.get("ticket")) : null;
        this.title = _super.title;
        this.writer = _super.writer;
    }

}

