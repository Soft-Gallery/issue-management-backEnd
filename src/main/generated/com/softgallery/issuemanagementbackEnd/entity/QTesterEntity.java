package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QTesterEntity is a Querydsl query type for TesterEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTesterEntity extends EntityPathBase<TesterEntity> {

    private static final long serialVersionUID = 1516222666L;

    public static final QTesterEntity testerEntity = new QTesterEntity("testerEntity");

    public final QUserEntity _super = new QUserEntity(this);

    //inherited
    public final StringPath email = _super.email;

    //inherited
    public final StringPath name = _super.name;

    //inherited
    public final StringPath password = _super.password;

    public final EnumPath<com.softgallery.issuemanagementbackEnd.service.user.Role> role = createEnum("role", com.softgallery.issuemanagementbackEnd.service.user.Role.class);

    //inherited
    public final StringPath userId = _super.userId;

    public QTesterEntity(String variable) {
        super(TesterEntity.class, forVariable(variable));
    }

    public QTesterEntity(Path<? extends TesterEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTesterEntity(PathMetadata metadata) {
        super(TesterEntity.class, metadata);
    }

}

