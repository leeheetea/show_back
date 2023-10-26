package com.showback.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QShowSchedule is a Querydsl query type for ShowSchedule
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShowSchedule extends EntityPathBase<ShowSchedule> {

    private static final long serialVersionUID = 1170096470L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QShowSchedule showSchedule = new QShowSchedule("showSchedule");

    public final DatePath<java.time.LocalDate> scheduleDate = createDate("scheduleDate", java.time.LocalDate.class);

    public final NumberPath<Long> scheduleId = createNumber("scheduleId", Long.class);

    public final TimePath<java.time.LocalTime> scheduleTime = createTime("scheduleTime", java.time.LocalTime.class);

    public final QShow show;

    public QShowSchedule(String variable) {
        this(ShowSchedule.class, forVariable(variable), INITS);
    }

    public QShowSchedule(Path<? extends ShowSchedule> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QShowSchedule(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QShowSchedule(PathMetadata metadata, PathInits inits) {
        this(ShowSchedule.class, metadata, inits);
    }

    public QShowSchedule(Class<? extends ShowSchedule> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.show = inits.isInitialized("show") ? new QShow(forProperty("show"), inits.get("show")) : null;
    }

}

