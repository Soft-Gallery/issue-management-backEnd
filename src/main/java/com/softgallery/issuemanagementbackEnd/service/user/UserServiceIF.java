package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.user.UserEntity;
import java.util.List;

public interface UserServiceIF {
    boolean createUser(UserDTO userDTO);
    UserDTO getUser(String id);

    UserDTO getUserById(String id);

    void updateUser(UserDTO userDTO, String id);
    void deleteUser(String id);

    List<UserDTO> getAllUserByRole(Role role);

    UserDTO switchUserEntityToDTO(UserEntity userEntity);
}
