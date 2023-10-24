package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTermsOfService is a Querydsl query type for TermsOfService
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTermsOfService extends EntityPathBase<TermsOfService> {

    private static final long serialVersionUID = -1725949127L;

    public static final QTermsOfService termsOfService = new QTermsOfService("termsOfService");

    public final BooleanPath isAccepted = createBoolean("isAccepted");

    public final NumberPath<Long> termsId = createNumber("termsId", Long.class);

    public final ListPath<UserAgreement, QUserAgreement> userAgreements = this.<UserAgreement, QUserAgreement>createList("userAgreements", UserAgreement.class, QUserAgreement.class, PathInits.DIRECT2);

    public QTermsOfService(String variable) {
        super(TermsOfService.class, forVariable(variable));
    }

    public QTermsOfService(Path<? extends TermsOfService> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTermsOfService(PathMetadata metadata) {
        super(TermsOfService.class, metadata);
    }

}

