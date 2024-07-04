package com.bandit.domain.music.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPromotionMusic is a Querydsl query type for PromotionMusic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPromotionMusic extends EntityPathBase<PromotionMusic> {

    private static final long serialVersionUID = -1009809925L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPromotionMusic promotionMusic = new QPromotionMusic("promotionMusic");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isOpen = createBoolean("isOpen");

    public final QMusic music;

    public final com.bandit.domain.board.entity.QPromotion promotion;

    public QPromotionMusic(String variable) {
        this(PromotionMusic.class, forVariable(variable), INITS);
    }

    public QPromotionMusic(Path<? extends PromotionMusic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPromotionMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPromotionMusic(PathMetadata metadata, PathInits inits) {
        this(PromotionMusic.class, metadata, inits);
    }

    public QPromotionMusic(Class<? extends PromotionMusic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.music = inits.isInitialized("music") ? new QMusic(forProperty("music")) : null;
        this.promotion = inits.isInitialized("promotion") ? new com.bandit.domain.board.entity.QPromotion(forProperty("promotion"), inits.get("promotion")) : null;
    }

}

