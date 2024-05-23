package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QStatisticsEntity is a Querydsl query type for StatisticsEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStatisticsEntity extends EntityPathBase<StatisticsEntity> {

    private static final long serialVersionUID = 1104557614L;

    public static final QStatisticsEntity statisticsEntity = new QStatisticsEntity("statisticsEntity");

    public final NumberPath<Long> issueId = createNumber("issueId", Long.class);

    public final NumberPath<Long> statisticsId = createNumber("statisticsId", Long.class);

    public QStatisticsEntity(String variable) {
        super(StatisticsEntity.class, forVariable(variable));
    }

    public QStatisticsEntity(Path<? extends StatisticsEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStatisticsEntity(PathMetadata metadata) {
        super(StatisticsEntity.class, metadata);
    }

}

