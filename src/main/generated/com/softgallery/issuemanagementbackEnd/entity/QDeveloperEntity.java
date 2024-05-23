package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeveloperEntity is a Querydsl query type for DeveloperEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeveloperEntity extends EntityPathBase<DeveloperEntity> {

    private static final long serialVersionUID = 1913869317L;

    public static final QDeveloperEntity developerEntity = new QDeveloperEntity("developerEntity");

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

    public QDeveloperEntity(String variable) {
        super(DeveloperEntity.class, forVariable(variable));
    }

    public QDeveloperEntity(Path<? extends DeveloperEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeveloperEntity(PathMetadata metadata) {
        super(DeveloperEntity.class, metadata);
    }

}

