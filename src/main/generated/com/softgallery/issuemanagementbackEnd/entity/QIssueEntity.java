package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QIssueEntity is a Querydsl query type for IssueEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QIssueEntity extends EntityPathBase<IssueEntity> {

    private static final long serialVersionUID = -1424266796L;

    public static final QIssueEntity issueEntity = new QIssueEntity("issueEntity");

    public final NumberPath<Long> assigneeId = createNumber("assigneeId", Long.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> fixerId = createNumber("fixerId", Long.class);

    public final NumberPath<Long> issueId = createNumber("issueId", Long.class);

    public final StringPath priority = createString("priority");

    public final NumberPath<Long> projectId = createNumber("projectId", Long.class);

    public final NumberPath<Long> reporterId = createNumber("reporterId", Long.class);

    public final StringPath status = createString("status");

    public final StringPath title = createString("title");

    public QIssueEntity(String variable) {
        super(IssueEntity.class, forVariable(variable));
    }

    public QIssueEntity(Path<? extends IssueEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QIssueEntity(PathMetadata metadata) {
        super(IssueEntity.class, metadata);
    }

}

