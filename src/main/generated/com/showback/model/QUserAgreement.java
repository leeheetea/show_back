package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserAgreement is a Querydsl query type for UserAgreement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserAgreement extends EntityPathBase<UserAgreement> {

    private static final long serialVersionUID = 1360097821L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserAgreement userAgreement = new QUserAgreement("userAgreement");

    public final BooleanPath agreed = createBoolean("agreed");

    public final DateTimePath<java.util.Date> agreementDate = createDateTime("agreementDate", java.util.Date.class);

    public final QTermsOfService termsOfService;

    public final QUser user;

    public final NumberPath<Long> userAgreementId = createNumber("userAgreementId", Long.class);

    public QUserAgreement(String variable) {
        this(UserAgreement.class, forVariable(variable), INITS);
    }

    public QUserAgreement(Path<? extends UserAgreement> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserAgreement(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserAgreement(PathMetadata metadata, PathInits inits) {
        this(UserAgreement.class, metadata, inits);
    }

    public QUserAgreement(Class<? extends UserAgreement> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.termsOfService = inits.isInitialized("termsOfService") ? new QTermsOfService(forProperty("termsOfService")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

