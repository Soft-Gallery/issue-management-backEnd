package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
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
    public void updateUser(final UserDTO userDTO, final Long id) {

    }

    @Override
    public void deleteUser(final Long id) {

    }

    @Override
    public void initialSetting() {
        int[] userNums = {1, 5, 2, 10};
        Role[] role = {Role.ROLE_ADMIN, Role.ROLE_TESTER, Role.ROLE_PL, Role.ROLE_DEVELOPER};
        String[] idAndPasses = {"admin", "tester", "PL", "DEV"};

        for(int i=0; i<4; i++) {
            for(int j=0; j<userNums[i]; j++) {
                UserEntity userEntity = UserEntityFactory.createUserEntity(role[i]);
                userEntity.setUserId(idAndPasses[i]+0+j);
                userEntity.setPassword(bCryptPasswordEncoder.encode(idAndPasses[i]+"00"+j));
                userEntity.setName(idAndPasses[i]+"_name"+j);
                userEntity.setEmail((idAndPasses[i]+j+"@"+idAndPasses[i]+".com"));

                userRepository.save(userEntity);
            }
        }
    }

    public boolean isValidPassword(String password) {
        String patternString = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,16}$";
        Pattern pattern = Pattern.compile(patternString);

        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public UserDTO switchUserEntityToDTO(UserEntity userEntity) {
        if(userEntity==null) return null;
        return new UserDTO(userEntity.getUserId(), userEntity.getName(),
        userEntity.getEmail(), "garbage", userEntity.getRole());
    }
}

