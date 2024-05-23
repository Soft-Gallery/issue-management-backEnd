package com.softgallery.issuemanagementbackEnd.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAdminEntity is a Querydsl query type for AdminEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAdminEntity extends EntityPathBase<AdminEntity> {

    private static final long serialVersionUID = 932680650L;

    public static final QAdminEntity adminEntity = new QAdminEntity("adminEntity");

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

    public QAdminEntity(String variable) {
        super(AdminEntity.class, forVariable(variable));
    }

    public QAdminEntity(Path<? extends AdminEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAdminEntity(PathMetadata metadata) {
        super(AdminEntity.class, metadata);
    }

}

