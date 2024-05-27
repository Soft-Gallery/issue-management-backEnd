package com.softgallery.issuemanagementbackEnd.entity.user;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_ADMIN")
public class AdminEntity extends UserEntity {
    public void manageUsers() { }

    @Override
    public Role getRole() {
        return Role.ROLE_ADMIN;
    }
}
