package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.UserDTO;

public interface UserServiceIF {
    void createUser(UserDTO userDTO);
    UserDTO getUser(Long id);
    void updateUser(UserDTO userDTO, Long id);
    void deleteUser(Long id);
}
