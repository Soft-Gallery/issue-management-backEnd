package com.softgallery.issuemanagementbackEnd.dto;

import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.service.user.Role;
import lombok.Getter;

@Getter
public class UserDTO {
    private String id;
    private String name;
    private String email;
    private String password;
    private Role role;

    public UserDTO() { }

    public UserDTO(final String id, final String name, final String email, final String password, final Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password=password;
        this.role = role;
    }

    public UserDTO(UserEntity userEntity) {
        this.id=userEntity.getUserId();
        this.name = userEntity.getName();
        this.email = userEntity.getEmail();
        this.role = userEntity.getRole();
    }
}
