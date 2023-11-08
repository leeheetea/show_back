package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1083807123L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUser user = new QUser("user");

    public final ListPath<LoginLog, QLoginLog> loginLogs = this.<LoginLog, QLoginLog>createList("loginLogs", LoginLog.class, QLoginLog.class, PathInits.DIRECT2);

    public final NumberPath<Integer> loginType = createNumber("loginType", Integer.class);

    public final QPassword password;

    public final QSocialLogin socialLogin;

    public final ListPath<UserAgreement, QUserAgreement> userAgreements = this.<UserAgreement, QUserAgreement>createList("userAgreements", UserAgreement.class, QUserAgreement.class, PathInits.DIRECT2);

    public final QUserAuth userAuth;

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final StringPath username = createString("username");

    public QUser(String variable) {
        this(User.class, forVariable(variable), INITS);
    }

    public QUser(Path<? extends User> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUser(PathMetadata metadata, PathInits inits) {
        this(User.class, metadata, inits);
    }

    public QUser(Class<? extends User> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.password = inits.isInitialized("password") ? new QPassword(forProperty("password"), inits.get("password")) : null;
        this.socialLogin = inits.isInitialized("socialLogin") ? new QSocialLogin(forProperty("socialLogin"), inits.get("socialLogin")) : null;
        this.userAuth = inits.isInitialized("userAuth") ? new QUserAuth(forProperty("userAuth"), inits.get("userAuth")) : null;
    }

}

