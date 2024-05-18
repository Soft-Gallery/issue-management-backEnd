package com.softgallery.issuemanagementbackEnd.dto;

import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role;

    public UserDTO() { }

    public UserDTO(final Long id, final String name, final String email, final String role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
    }
}
