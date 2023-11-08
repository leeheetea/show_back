package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShow is a Querydsl query type for Show
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShow extends EntityPathBase<Show> {

    private static final long serialVersionUID = -1083876961L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShow show = new QShow("show");

    public final StringPath contentDetail = createString("contentDetail");

    public final StringPath period = createString("period");

    public final NumberPath<Integer> price = createNumber("price", Integer.class);

    public final ListPath<Reservation, QReservation> reservations = this.<Reservation, QReservation>createList("reservations", Reservation.class, QReservation.class, PathInits.DIRECT2);

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final QShowBanner showBanner;

    public final NumberPath<Long> showId = createNumber("showId", Long.class);

    public final ListPath<ShowSchedule, QShowSchedule> showSchedules = this.<ShowSchedule, QShowSchedule>createList("showSchedules", ShowSchedule.class, QShowSchedule.class, PathInits.DIRECT2);

    public final ListPath<ShowSeat, QShowSeat> showSeats = this.<ShowSeat, QShowSeat>createList("showSeats", ShowSeat.class, QShowSeat.class, PathInits.DIRECT2);

    public final StringPath thumbnailUrl = createString("thumbnailUrl");

    public final StringPath title = createString("title");

    public final StringPath type = createString("type");

    public final QVenue venue;

    public QShow(String variable) {
        this(Show.class, forVariable(variable), INITS);
    }

    public QShow(Path<? extends Show> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShow(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShow(PathMetadata metadata, PathInits inits) {
        this(Show.class, metadata, inits);
    }

    public QShow(Class<? extends Show> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.showBanner = inits.isInitialized("showBanner") ? new QShowBanner(forProperty("showBanner"), inits.get("showBanner")) : null;
        this.venue = inits.isInitialized("venue") ? new QVenue(forProperty("venue")) : null;
    }

}

