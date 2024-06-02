package com.softgallery.issuemanagementbackEnd.service.custom_annotation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class IDRuleTest {

    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    @DisplayName("올바른 ID 테스트")
    void testValidID() {
        TestClass testClass = new TestClass("abc123");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(testClass);
        assertTrue(violations.isEmpty(), "ID가 올바르므로 검증 오류가 없어야 합니다.");
    }

    @Test
    @DisplayName("잘못된 ID 테스트 - 숫자만 포함")
    void testInvalidID_NumbersOnly() {
        TestClass testClass = new TestClass("123456");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(testClass);
        assertFalse(violations.isEmpty(), "숫자만 포함한 ID는 검증 오류가 있어야 합니다.");
    }

    @Test
    @DisplayName("잘못된 ID 테스트 - 문자만 포함")
    void testInvalidID_LettersOnly() {
        TestClass testClass = new TestClass("abcdef");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(testClass);
        assertFalse(violations.isEmpty(), "문자만 포함한 ID는 검증 오류가 있어야 합니다.");
    }

    @Test
    @DisplayName("잘못된 ID 테스트 - 길이 초과")
    void testInvalidID_LengthTooLong() {
        TestClass testClass = new TestClass("abc123456");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(testClass);
        assertFalse(violations.isEmpty(), "8자리를 초과하는 ID는 검증 오류가 있어야 합니다.");
    }

    @Test
    @DisplayName("잘못된 ID 테스트 - 길이 부족")
    void testInvalidID_LengthTooShort() {
        TestClass testClass = new TestClass("ab1");
        Set<ConstraintViolation<TestClass>> violations = validator.validate(testClass);
        assertFalse(violations.isEmpty(), "4자리보다 짧은 ID는 검증 오류가 있어야 합니다.");
    }

    private static class TestClass {
        @IDRule
        private final String id;

        public TestClass(String id) {
            this.id = id;
        }
    }
}
