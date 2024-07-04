package com.bandit.domain.like.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeMusic is a Querydsl query type for LikeMusic
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeMusic extends EntityPathBase<LikeMusic> {

    private static final long serialVersionUID = -888876207L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeMusic likeMusic = new QLikeMusic("likeMusic");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.bandit.domain.member.entity.QMember member;

    public final NumberPath<Long> promotionMusicId = createNumber("promotionMusicId", Long.class);

    public QLikeMusic(String variable) {
        this(LikeMusic.class, forVariable(variable), INITS);
    }

    public QLikeMusic(Path<? extends LikeMusic> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeMusic(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeMusic(PathMetadata metadata, PathInits inits) {
        this(LikeMusic.class, metadata, inits);
    }

    public QLikeMusic(Class<? extends LikeMusic> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.bandit.domain.member.entity.QMember(forProperty("member")) : null;
    }

}

