package com.bandit.domain.ticket.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTicket is a Querydsl query type for Ticket
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTicket extends EntityPathBase<Ticket> {

    private static final long serialVersionUID = 1768589278L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QTicket ticket = new QTicket("ticket");

    public final com.bandit.domain.auditing.entity.QBaseTimeEntity _super = new com.bandit.domain.auditing.entity.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final DatePath<java.time.LocalDate> dueDate = createDate("dueDate", java.time.LocalDate.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final com.bandit.domain.board.entity.QPromotion promotion;

    public final StringPath uuid = createString("uuid");

    public QTicket(String variable) {
        this(Ticket.class, forVariable(variable), INITS);
    }

    public QTicket(Path<? extends Ticket> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QTicket(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QTicket(PathMetadata metadata, PathInits inits) {
        this(Ticket.class, metadata, inits);
    }

    public QTicket(Class<? extends Ticket> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.promotion = inits.isInitialized("promotion") ? new com.bandit.domain.board.entity.QPromotion(forProperty("promotion"), inits.get("promotion")) : null;
    }

}

