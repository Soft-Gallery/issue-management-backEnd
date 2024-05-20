package com.softgallery.issuemanagementbackEnd.entity;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PL")
public class PLEntity extends UserEntity {
    public void manageProject() { }
    public void assignTasks() { }

    @Override
    public Role getRole() {
        return Role.PL;
    }
}
