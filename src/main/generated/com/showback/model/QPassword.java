package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPassword is a Querydsl query type for Password
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPassword extends EntityPathBase<Password> {

    private static final long serialVersionUID = -793438147L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPassword password = new QPassword("password");

    public final NumberPath<Long> passwordId = createNumber("passwordId", Long.class);

    public final StringPath salt = createString("salt");

    public final DateTimePath<java.util.Date> updateDate = createDateTime("updateDate", java.util.Date.class);

    public final QUser user;

    public final StringPath userPassword = createString("userPassword");

    public QPassword(String variable) {
        this(Password.class, forVariable(variable), INITS);
    }

    public QPassword(Path<? extends Password> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPassword(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPassword(PathMetadata metadata, PathInits inits) {
        this(Password.class, metadata, inits);
    }

    public QPassword(Class<? extends Password> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user"), inits.get("user")) : null;
    }

}

