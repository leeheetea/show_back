package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAuth is a Querydsl query type for UserAuth
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAuth extends EntityPathBase<UserAuth> {

    private static final long serialVersionUID = 2017508789L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAuth userAuth = new QUserAuth("userAuth");

    public final DateTimePath<java.util.Date> authDate = createDateTime("authDate", java.util.Date.class);

    public final StringPath authEmail = createString("authEmail");

    public final NumberPath<Long> authId = createNumber("authId", Long.class);

    public final StringPath authName = createString("authName");

    public final StringPath authPhone = createString("authPhone");

    public final ListPath<Inquiry, QInquiry> inquiryList = this.<Inquiry, QInquiry>createList("inquiryList", Inquiry.class, QInquiry.class, PathInits.DIRECT2);

    public final ListPath<Notice, QNotice> noticeList = this.<Notice, QNotice>createList("noticeList", Notice.class, QNotice.class, PathInits.DIRECT2);

    public final ListPath<Order, QOrder> orderList = this.<Order, QOrder>createList("orderList", Order.class, QOrder.class, PathInits.DIRECT2);

    public final ListPath<Review, QReview> reviews = this.<Review, QReview>createList("reviews", Review.class, QReview.class, PathInits.DIRECT2);

    public final BooleanPath smsChoice = createBoolean("smsChoice");

    public final QUser user;

    public final StringPath validatePeriod = createString("validatePeriod");

    public QUserAuth(String variable) {
        this(UserAuth.class, forVariable(variable), INITS);
    }

    public QUserAuth(Path<? extends UserAuth> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAuth(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAuth(PathMetadata metadata, PathInits inits) {
        this(UserAuth.class, metadata, inits);
    }

    public QUserAuth(Class<? extends UserAuth> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

