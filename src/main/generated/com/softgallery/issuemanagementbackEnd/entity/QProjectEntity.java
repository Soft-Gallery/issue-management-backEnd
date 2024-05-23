package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProjectEntity is a Querydsl query type for ProjectEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProjectEntity extends EntityPathBase<ProjectEntity> {

    private static final long serialVersionUID = 384585236L;

    public static final QProjectEntity projectEntity = new QProjectEntity("projectEntity");

    public final StringPath adminId = createString("adminId");

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> endDate = createDateTime("endDate", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final EnumPath<com.softgallery.issuemanagementbackEnd.service.project.ProjectState> projectState = createEnum("projectState", com.softgallery.issuemanagementbackEnd.service.project.ProjectState.class);

    public final DateTimePath<java.time.LocalDateTime> startDate = createDateTime("startDate", java.time.LocalDateTime.class);

    public QProjectEntity(String variable) {
        super(ProjectEntity.class, forVariable(variable));
    }

    public QProjectEntity(Path<? extends ProjectEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProjectEntity(PathMetadata metadata) {
        super(ProjectEntity.class, metadata);
    }

}

