
package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.entity.*;

public class UserEntityFactory {
    public static UserEntity createUserEntity(Role role) {
        switch (role) {
            case ADMIN:
                return new AdminEntity();
            case PL:
                return new PLEntity();
            case TESTER:
                return new TesterEntity();
            case DEVELOPER:
                return new DeveloperEntity();
            default:
                throw new IllegalArgumentException("Unsupported role: " + role);
        }
    }
}

