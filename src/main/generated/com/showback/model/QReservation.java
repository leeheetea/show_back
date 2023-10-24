package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReservation is a Querydsl query type for Reservation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReservation extends EntityPathBase<Reservation> {

    private static final long serialVersionUID = -782603542L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReservation reservation = new QReservation("reservation");

    public final QOrder order;

    public final DateTimePath<java.util.Date> reservationDate = createDateTime("reservationDate", java.util.Date.class);

    public final NumberPath<Long> reservationId = createNumber("reservationId", Long.class);

    public final StringPath reservationPrice = createString("reservationPrice");

    public final DateTimePath<java.util.Date> reservationShowDate = createDateTime("reservationShowDate", java.util.Date.class);

    public final StringPath reservationShowName = createString("reservationShowName");

    public final StringPath reservationShowVenue = createString("reservationShowVenue");

    public final StringPath reservationState = createString("reservationState");

    public final NumberPath<Integer> reservationTicketAmount = createNumber("reservationTicketAmount", Integer.class);

    public final QSeat seat;

    public final QShow show;

    public QReservation(String variable) {
        this(Reservation.class, forVariable(variable), INITS);
    }

    public QReservation(Path<? extends Reservation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReservation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReservation(PathMetadata metadata, PathInits inits) {
        this(Reservation.class, metadata, inits);
    }

    public QReservation(Class<? extends Reservation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.order = inits.isInitialized("order") ? new QOrder(forProperty("order"), inits.get("order")) : null;
        this.seat = inits.isInitialized("seat") ? new QSeat(forProperty("seat"), inits.get("seat")) : null;
        this.show = inits.isInitialized("show") ? new QShow(forProperty("show"), inits.get("show")) : null;
    }

}

