package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSeat is a Querydsl query type for Seat
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSeat extends EntityPathBase<Seat> {

    private static final long serialVersionUID = -1083880281L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSeat seat = new QSeat("seat");

    public final NumberPath<Integer> seatColumn = createNumber("seatColumn", Integer.class);

    public final NumberPath<Long> seatId = createNumber("seatId", Long.class);

    public final NumberPath<Integer> seatRow = createNumber("seatRow", Integer.class);

    public final ListPath<ShowSeat, QShowSeat> showSeats = this.<ShowSeat, QShowSeat>createList("showSeats", ShowSeat.class, QShowSeat.class, PathInits.DIRECT2);

    public final QVenue venue;

    public QSeat(String variable) {
        this(Seat.class, forVariable(variable), INITS);
    }

    public QSeat(Path<? extends Seat> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSeat(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSeat(PathMetadata metadata, PathInits inits) {
        this(Seat.class, metadata, inits);
    }

    public QSeat(Class<? extends Seat> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.venue = inits.isInitialized("venue") ? new QVenue(forProperty("venue")) : null;
    }

}

