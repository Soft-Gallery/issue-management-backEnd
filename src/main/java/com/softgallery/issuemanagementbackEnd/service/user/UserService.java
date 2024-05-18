package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserServiceIF {
    private final UserRepository userRepository;

    public UserService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(final UserDTO userDTO) {

    }

    @Override
    public UserDTO getUser(final Long id) {
        return null;
    }

    @Override
    public void updateUser(final UserDTO userDTO, final Long id) {

    }

    @Override
    public void deleteUser(final Long id) {

    }
}
