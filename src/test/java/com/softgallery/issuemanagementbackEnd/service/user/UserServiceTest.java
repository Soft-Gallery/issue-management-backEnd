package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.authentication.JWTUtil;
import com.softgallery.issuemanagementbackEnd.dto.user.UserDTO;
import com.softgallery.issuemanagementbackEnd.entity.user.UserEntity;
import com.softgallery.issuemanagementbackEnd.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private JWTUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("사용자 생성 테스트")
    void testCreateUser() {
        // given
        UserDTO userDTO = new UserDTO("user123", "User Name", "user@example.com", "password123", Role.ROLE_DEVELOPER);
        when(userRepository.existsByUserId(anyString())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(UserEntity.class))).thenReturn(new UserEntity() {
            @Override
            public Role getRole() {
                return Role.ROLE_DEVELOPER;
            }
        });

        // when
        boolean result = userService.createUser(userDTO);

        // then
        assertTrue(result);
        verify(userRepository, times(1)).existsByUserId(anyString());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    @DisplayName("토큰으로 사용자 정보 가져오기 테스트")
    void testGetUser() {
        // given
        String token = "Bearer token";
        String userId = "user123";
        UserEntity userEntity = new UserEntity() {
            @Override
            public Role getRole() {
                return Role.ROLE_DEVELOPER;
            }
        };
        userEntity.setUserId(userId);
        userEntity.setName("User Name");
        userEntity.setEmail("user@example.com");

        when(jwtUtil.getUserId(anyString())).thenReturn(userId);
        when(userRepository.findByUserId(userId)).thenReturn(userEntity);

        // when
        UserDTO result = userService.getUser(token);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(jwtUtil, times(1)).getUserId(anyString());
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("ID로 사용자 정보 가져오기 테스트")
    void testGetUserById() {
        // given
        String userId = "user123";
        UserEntity userEntity = new UserEntity() {
            @Override
            public Role getRole() {
                return Role.ROLE_DEVELOPER;
            }
        };
        userEntity.setUserId(userId);
        userEntity.setName("User Name");
        userEntity.setEmail("user@example.com");

        when(userRepository.findByUserId(userId)).thenReturn(userEntity);

        // when
        UserDTO result = userService.getUserById(userId);

        // then
        assertNotNull(result);
        assertEquals(userId, result.getId());
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("사용자 업데이트 테스트")
    void testUpdateUser() {
        // given
        String userId = "user123";
        UserDTO userDTO = new UserDTO(userId, "Updated Name", "updated@example.com", "password123", Role.ROLE_DEVELOPER);
        UserEntity userEntity = new UserEntity() {
            @Override
            public Role getRole() {
                return Role.ROLE_DEVELOPER;
            }
        };
        userEntity.setUserId(userId);

        when(userRepository.findByUserId(userId)).thenReturn(userEntity);

        // when
        userService.updateUser(userDTO, userId);

        // then
        assertEquals("Updated Name", userEntity.getName());
        assertEquals("updated@example.com", userEntity.getEmail());
        verify(userRepository, times(1)).findByUserId(userId);
    }

    @Test
    @DisplayName("사용자 삭제 테스트")
    void testDeleteUser() {
        // given
        String userId = "user123";

        // when
        userService.deleteUser(userId);

        // then
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    @DisplayName("역할별 사용자 목록 가져오기 테스트")
    void testGetAllUserByRole() {
        // given
        Role role = Role.ROLE_DEVELOPER;
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity userEntity = new UserEntity() {
            @Override
            public Role getRole() {
                return Role.ROLE_DEVELOPER;
            }
        };
        userEntity.setUserId("user123");
        userEntity.setName("User Name");
        userEntity.setEmail("user@example.com");
        userEntities.add(userEntity);

        when(userRepository.findAllByRole(role)).thenReturn(userEntities);

        // when
        List<UserDTO> result = userService.getAllUserByRole(role);

        // then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("user123", result.get(0).getId());
        verify(userRepository, times(1)).findAllByRole(role);
    }

    @Test
    @DisplayName("비밀번호 유효성 검사 테스트")
    void testIsValidPassword() {
        // given
        String validPassword = "password123";
        String invalidPassword = "pass";

        // when
        boolean isValid = userService.isValidPassword(validPassword);
        boolean isInvalid = userService.isValidPassword(invalidPassword);

        // then
        assertTrue(isValid);
        assertFalse(isInvalid);
    }
}
