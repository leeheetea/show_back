package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QVenue is a Querydsl query type for Venue
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVenue extends EntityPathBase<Venue> {

    private static final long serialVersionUID = 762232845L;

    public static final QVenue venue = new QVenue("venue");

    public final NumberPath<Integer> seatMaxColumn = createNumber("seatMaxColumn", Integer.class);

    public final NumberPath<Integer> seatMaxRow = createNumber("seatMaxRow", Integer.class);

    public final ListPath<Seat, QSeat> seats = this.<Seat, QSeat>createList("seats", Seat.class, QSeat.class, PathInits.DIRECT2);

    public final ListPath<Show, QShow> shows = this.<Show, QShow>createList("shows", Show.class, QShow.class, PathInits.DIRECT2);

    public final StringPath venueAddress = createString("venueAddress");

    public final NumberPath<Long> venueId = createNumber("venueId", Long.class);

    public final StringPath venueName = createString("venueName");

    public QVenue(String variable) {
        super(Venue.class, forVariable(variable));
    }

    public QVenue(Path<? extends Venue> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVenue(PathMetadata metadata) {
        super(Venue.class, metadata);
    }

}

