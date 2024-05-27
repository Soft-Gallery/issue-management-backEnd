package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.user.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.user.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements UserServiceIF {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JWTUtil jwtUtil;

    public UserService(final UserRepository userRepository, final BCryptPasswordEncoder bCryptPasswordEncoder, JWTUtil jwtUtil) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        this.jwtUtil = jwtUtil;
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

            userRepository.save(userData);
            return true;
        }
        catch (IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public UserDTO getUser(final String fullToken) {
        String realToken = JWTUtil.getOnlyToken(fullToken);
        String userId = jwtUtil.getUserId(realToken);
        UserEntity userEntity = userRepository.findByUserId(userId);
        UserDTO userDTO = switchUserEntityToDTO(userEntity);
        return userDTO;
    }

    @Override
    public void updateUser(final UserDTO userDTO, final String id) {
        UserEntity user = userRepository.findByUserId(id);
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
    }

    @Override
    public void deleteUser(final String id) {
        userRepository.deleteById(id);
    }

    public boolean isValidPassword(String password) {
        String patternString = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    @Override
    public UserDTO switchUserEntityToDTO(UserEntity userEntity) {
        if(userEntity==null) return null;
        return new UserDTO(userEntity.getUserId(), userEntity.getName(),
        userEntity.getEmail(), "garbage", userEntity.getRole());
    }
}

