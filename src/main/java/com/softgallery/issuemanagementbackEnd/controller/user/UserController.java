package com.softgallery.issuemanagementbackEnd.controller.user;

import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    private UserServiceIF userService;

    public UserController(final UserServiceIF userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public boolean CreateUser(@RequestBody UserDTO userDTO) {
        System.out.println(userDTO.getRole());
        return userService.createUser(userDTO);
    }

}
