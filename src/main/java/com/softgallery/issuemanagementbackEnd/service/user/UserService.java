package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import com.softgallery.issuemanagementbackEnd.util.custom_annotation.PasswordRule;
import lombok.NonNull;
import org.apache.catalina.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserServiceIF {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }

    @Override
    public boolean createUser(final UserDTO userDTO) {
        String userId = userDTO.getId();
        Role userRole = userDTO.getRole();
        String userPassword=userDTO.getPassword();

        Boolean isExist = userRepository.existsByUserId(userId);

        if(isExist) return false;
        if(!isValidPassword(userPassword)) return false;

        try {
            UserEntity userData = UserEntityFactory.createUserEntity(userRole);
            userData.setUserId(userId);
            userData.setPassword(bCryptPasswordEncoder.encode(userPassword));
            userData.setName(userDTO.getName());
            userData.setEmail(userDTO.getEmail());
            userData.setRole(userDTO.getRole());

            userRepository.save(userData);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
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

    public boolean isValidPassword(String password) {
        String patternString = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
}

