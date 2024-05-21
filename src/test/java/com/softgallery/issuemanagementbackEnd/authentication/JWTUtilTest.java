package com.softgallery.issuemanagementbackEnd.authentication;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JWTUtilTest {
    private JWTUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        // 시크릿 키는 테스트 목적을 위해 임의로 설정합니다.
        String secret = "testsecretkeytestsecretkeytestsecretkeytestsecretkey";
        jwtUtil = new JWTUtil(secret);
    }
    @Test
    @DisplayName("토큰에서 사용자 ID 가져오기")
    public void testGetUserId() {
        String token = jwtUtil.createJwt("testUserId", "user", 3600000L);
        String userId = jwtUtil.getUserId(token);
        assertEquals("testUserId", userId);
    }

    @Test
    @DisplayName("토큰에서 역할 가져오기")
    public void testGetRole() {
        String token = jwtUtil.createJwt("testUserId", "user", 3600000L);
        String role = jwtUtil.getRole(token);
        assertEquals("user", role);
    }

    @Test
    @DisplayName("전체 토큰에서 JWT 토큰 부분만 추출하기")
    public void testGetOnlyToken() {
        String token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJ0ZXN0VXNlcldyaXRlIiwicm9sZSI6InVzZXIiLCJleHAiOjE2MjA0NTkxMzAsImlhdCI6MTYyMDQ1OTAzMH0.8FLuAr5k8wB4yJc9ncJckG61NxvYfJ7Y8YbsNhoT4h8";
        String onlyToken = JWTUtil.getOnlyToken(token);
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VySWQiOiJ0ZXN0VXNlcldyaXRlIiwicm9sZSI6InVzZXIiLCJleHAiOjE2MjA0NTkxMzAsImlhdCI6MTYyMDQ1OTAzMH0.8FLuAr5k8wB4yJc9ncJckG61NxvYfJ7Y8YbsNhoT4h8", onlyToken);
    }

}