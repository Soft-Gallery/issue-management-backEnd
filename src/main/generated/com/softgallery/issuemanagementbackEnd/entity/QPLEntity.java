package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPLEntity is a Querydsl query type for PLEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPLEntity extends EntityPathBase<PLEntity> {

    private static final long serialVersionUID = -747033561L;

    public static final QPLEntity pLEntity = new QPLEntity("pLEntity");

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

    public QPLEntity(String variable) {
        super(PLEntity.class, forVariable(variable));
    }

    public QPLEntity(Path<? extends PLEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPLEntity(PathMetadata metadata) {
        super(PLEntity.class, metadata);
    }

}

