package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;

public interface UserServiceIF {
    boolean createUser(UserDTO userDTO);
    UserDTO getUser(Long id);
    void updateUser(UserDTO userDTO, Long id);
    void deleteUser(Long id);

    UserDTO switchUserEntityToDTO(UserEntity userEntity);
}
