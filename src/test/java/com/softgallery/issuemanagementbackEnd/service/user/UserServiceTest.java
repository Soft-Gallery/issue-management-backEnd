package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.dto.UserDTO;
import com.softgallery.issuemanagementbackEnd.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @Test
    @DisplayName("이미 존재하는 사용자를 추가하는 경우")
    public void testCreateUserWithExistingUser() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUserId("existingUser")).thenReturn(true);

        UserService userService = new UserService(userRepository, null);

        assertFalse(userService.createUser(new UserDTO("existingUser", "user", "user@example.com", "password", Role.ROLE_ADMIN)));
    }

    @Test
    @DisplayName("비밀번호가 유효하지 않은 경우")
    public void testCreateUserWithInvalidPassword() {
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUserId("newUser")).thenReturn(false);

        UserService userService = new UserService(userRepository, null);

        assertFalse(userService.createUser(new UserDTO("newUser", "user", "user@example.com", "short", Role.ROLE_ADMIN)));
    }

    @Test
    @DisplayName("유효한 사용자를 추가하는 경우")
    public void testCreateUserWithValidUser() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.existsByUserId("newUser")).thenReturn(false);

        UserService userService = new UserService(userRepository, bCryptPasswordEncoder);

        assertTrue(userService.createUser(new UserDTO("newUser", "user", "user@example.com", "StrongPw123", Role.ROLE_ADMIN)));
    }

    @Test
    @DisplayName("유효한 최대 비밀번호")
    public void testMaxValidPassword() {
        UserService userService = new UserService(null, null);
        assertTrue(userService.isValidPassword("GfoodPassword123"));
    }

    @Test
    @DisplayName("유효한 최소 비밀번호")
    public void testMinValidPassword() {
        UserService userService = new UserService(null, null);
        assertTrue(userService.isValidPassword("abcd1234"));
    }

    @Test
    @DisplayName("짧은 비밀번호")
    public void testShortPassword() {
        UserService userService = new UserService(null, null);
        assertFalse(userService.isValidPassword("short"));
    }

    @Test
    @DisplayName("숫자나 특수문자 없는 비밀번호")
    public void testNoDigitOrSpecialCharPassword() {
        UserService userService = new UserService(null, null);
        assertFalse(userService.isValidPassword("NoDigitOrSpecialChar"));
    }

    @Test
    @DisplayName("숫자만 있는 비밀번호")
    public void testOnlyDigitsPassword() {
        UserService userService = new UserService(null, null);
        assertFalse(userService.isValidPassword("12345678"));
    }

    @Test
    @DisplayName("대문자만 있는 비밀번호")
    public void testOnlyUpperCasePassword() {
        UserService userService = new UserService(null, null);
        assertFalse(userService.isValidPassword("OnlyUpperCase"));
    }

    @Test
    @DisplayName("소문자만 있는 비밀번호")
    public void testOnlyLowerCasePassword() {
        UserService userService = new UserService(null, null);
        assertFalse(userService.isValidPassword("onlylowercase"));
    }

    @Test
    @DisplayName("긴 비밀번호")
    public void testLongPassword() {
        UserService userService = new UserService(null, null);
        assertFalse(userService.isValidPassword("kfA1B2C3D4E5F6G7H"));
    }
}
