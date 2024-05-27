package com.softgallery.issuemanagementbackEnd.entity.user;

import com.softgallery.issuemanagementbackEnd.service.user.Role;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ROLE_TESTER")
public class TesterEntity extends UserEntity {
    public void writeTestCases() { }
    public void performTesting() { }

    @Override
    public Role getRole() {
        return Role.ROLE_TESTER;
    }
}
