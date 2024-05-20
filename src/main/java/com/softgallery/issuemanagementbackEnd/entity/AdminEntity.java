package com.softgallery.issuemanagementbackEnd.entity;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminEntity extends UserEntity {
    public void manageUsers() { }

    @Override
    public Role getRole() {
        return Role.ADMIN;
    }
}
