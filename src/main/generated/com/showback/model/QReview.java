package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QReview is a Querydsl query type for Review
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QReview extends EntityPathBase<Review> {

    private static final long serialVersionUID = 2040092026L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QReview review = new QReview("review");

    public final NumberPath<Integer> reviewGrade = createNumber("reviewGrade", Integer.class);

    public final NumberPath<Long> reviewId = createNumber("reviewId", Long.class);

    public final StringPath reviewText = createString("reviewText");

    public final DateTimePath<java.time.LocalDateTime> reviewTimestamp = createDateTime("reviewTimestamp", java.time.LocalDateTime.class);

    public final QShow show;

    public final QUserAuth userAuth;

    public QReview(String variable) {
        this(Review.class, forVariable(variable), INITS);
    }

    public QReview(Path<? extends Review> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QReview(PathMetadata metadata, PathInits inits) {
        this(Review.class, metadata, inits);
    }

    public QReview(Class<? extends Review> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.show = inits.isInitialized("show") ? new QShow(forProperty("show"), inits.get("show")) : null;
        this.userAuth = inits.isInitialized("userAuth") ? new QUserAuth(forProperty("userAuth"), inits.get("userAuth")) : null;
    }

}

