package com.softgallery.issuemanagementbackEnd.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class AdminEntity extends UserEntity {
    public void manageUsers() { }
}
