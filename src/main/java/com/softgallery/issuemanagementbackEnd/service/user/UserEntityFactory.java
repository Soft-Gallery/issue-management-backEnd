
package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.entity.*;

public class UserEntityFactory {
    public static UserEntity createUserEntity(Role role) {
        switch (role) {
            case ROLE_ADMIN:
                return new AdminEntity();
            case ROLE_PL:
                return new PLEntity();
            case ROLE_TESTER:
                return new TesterEntity();
            case ROLE_DEVELOPER:
                return new DeveloperEntity();
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}

