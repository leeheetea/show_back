package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShowBanner is a Querydsl query type for ShowBanner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShowBanner extends EntityPathBase<ShowBanner> {

    private static final long serialVersionUID = 996659627L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShowBanner showBanner = new QShowBanner("showBanner");

    public final StringPath bannerUrl = createString("bannerUrl");

    public final QShow show;

    public final NumberPath<Long> showBannerId = createNumber("showBannerId", Long.class);

    public final StringPath smallBannerUrl = createString("smallBannerUrl");

    public QShowBanner(String variable) {
        this(ShowBanner.class, forVariable(variable), INITS);
    }

    public QShowBanner(Path<? extends ShowBanner> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShowBanner(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShowBanner(PathMetadata metadata, PathInits inits) {
        this(ShowBanner.class, metadata, inits);
    }

    public QShowBanner(Class<? extends ShowBanner> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.show = inits.isInitialized("show") ? new QShow(forProperty("show"), inits.get("show")) : null;
    }

}

