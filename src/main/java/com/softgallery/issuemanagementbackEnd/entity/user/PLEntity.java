package com.softgallery.issuemanagementbackEnd.entity.user;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_PL")
public class PLEntity extends UserEntity {
    public void manageProject() { }
    public void assignTasks() { }

    @Override
    public Role getRole() {
        return Role.ROLE_PL;
    }
}
