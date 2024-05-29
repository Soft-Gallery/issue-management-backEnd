package com.softgallery.issuemanagementbackEnd.controller.user;

import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import com.softgallery.issuemanagementbackEnd.service.user.UserServiceIF;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController {
    private final UserServiceIF userService;

    public UserController(final UserServiceIF userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public boolean CreateUser(@RequestBody UserDTO userDTO) {
        return userService.createUser(userDTO);
    }

    @GetMapping("/info")
    public UserDTO getUser(@RequestHeader(name="Authorization") String token) {
        return this.userService.getUser(token);
    }

    @GetMapping("/role/{roleName}")
    public List<UserDTO> findAllByRole(@PathVariable("roleName") Role role) {
        return userService.getAllUserByRole(role);
    }
}
