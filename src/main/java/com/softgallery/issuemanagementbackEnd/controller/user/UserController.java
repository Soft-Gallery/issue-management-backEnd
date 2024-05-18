package com.softgallery.issuemanagementbackEnd.controller.user;

import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.springframework.stereotype.Controller;

@Controller
public class UserController {
    private UserServiceIF userService;

    public UserController(final UserServiceIF userService) {
        this.userService = userService;
    }
}
