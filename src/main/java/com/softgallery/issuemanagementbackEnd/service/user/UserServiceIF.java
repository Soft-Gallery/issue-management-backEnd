package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.user.UserEntity;

public interface UserServiceIF {
    boolean createUser(UserDTO userDTO);
    UserDTO getUser(String id);
    void updateUser(UserDTO userDTO, String id);
    void deleteUser(String id);

    UserDTO switchUserEntityToDTO(UserEntity userEntity);
}
