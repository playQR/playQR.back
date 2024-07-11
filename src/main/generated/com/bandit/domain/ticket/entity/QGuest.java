package com.bandit.domain.ticket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QGuest is a Querydsl query type for Guest
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGuest extends EntityPathBase<Guest> {

    private static final long serialVersionUID = -1340068154L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGuest guest = new QGuest("guest");

    public final com.bandit.domain.auditing.entity.QBaseTimeEntity _super = new com.bandit.domain.auditing.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DatePath<java.time.LocalDate> depositDate = createDate("depositDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isEntered = createBoolean("isEntered");

    public final BooleanPath isReservationCancelled = createBoolean("isReservationCancelled");

    public final BooleanPath isReservationConfirmed = createBoolean("isReservationConfirmed");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.bandit.domain.member.entity.QMember member;

    public final StringPath name = createString("name");

    public final com.bandit.domain.board.entity.QPromotion promotion;

    public final NumberPath<Integer> reservationCount = createNumber("reservationCount", Integer.class);

    public QGuest(String variable) {
        this(Guest.class, forVariable(variable), INITS);
    }

    public QGuest(Path<? extends Guest> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGuest(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGuest(PathMetadata metadata, PathInits inits) {
        this(Guest.class, metadata, inits);
    }

    public QGuest(Class<? extends Guest> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new com.bandit.domain.member.entity.QMember(forProperty("member")) : null;
        this.promotion = inits.isInitialized("promotion") ? new com.bandit.domain.board.entity.QPromotion(forProperty("promotion"), inits.get("promotion")) : null;
    }

}

