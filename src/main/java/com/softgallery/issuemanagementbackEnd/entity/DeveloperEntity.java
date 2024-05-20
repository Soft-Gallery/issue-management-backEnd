package com.softgallery.issuemanagementbackEnd.entity;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_DEVELOPER")
public class DeveloperEntity extends UserEntity {
    public void writeCode() { }
    public void fixBugs() { }

    @Override
    public Role getRole() {
        return Role.ROLE_DEVELOPER;
    }
}
