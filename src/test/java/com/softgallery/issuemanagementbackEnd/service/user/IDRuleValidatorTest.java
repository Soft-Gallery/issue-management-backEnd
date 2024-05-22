package com.softgallery.issuemanagementbackEnd.service.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IDRuleValidatorTest {

    private final IDRuleValidator validator = new IDRuleValidator();

    @Test
    @DisplayName("abc123")
    void testValidID_abc123() {
        String validID = "abc123";
        assertTrue(validator.isValid(validID, null));
    }

    @Test
    @DisplayName("jlw12")
    void testValidID_jlw12() {
        String validID = "jlw12";
        assertTrue(validator.isValid(validID, null));
    }

    @Test
    @DisplayName("24kaf")
    void testValidID_24kaf() {
        String validID = "24kaf";
        assertTrue(validator.isValid(validID, null));
    }

    @Test
    @DisplayName("1b1b1b")
    void testValidID_1b1b1b() {
        String validID = "1b1b1b";
        assertTrue(validator.isValid(validID, null));
    }

    @Test
    @DisplayName("j2l1l1")
    void testValidID_j2l1l1() {
        String validID = "j2l1l1";
        assertTrue(validator.isValid(validID, null));
    }

    @Test
    @DisplayName("No letter fail")
    void testInvalidID_NoLetters() {
        String invalidID = "123456";
        assertFalse(validator.isValid(invalidID, null));
    }

    @Test
    @DisplayName("No number fail")
    void testInvalidID_NoNumbers() {
        String invalidID = "abcdef";
        assertFalse(validator.isValid(invalidID, null));
    }

    @Test
    @DisplayName("Too short fail")
    void testInvalidID_TooShort() {
        String invalidID = "ab1";
        assertFalse(validator.isValid(invalidID, null));
    }

    @Test
    @DisplayName("Too long fail")
    void testInvalidID_TooLong() {
        String invalidID = "abcdefghi";
        assertFalse(validator.isValid(invalidID, null));
    }

    @Test
    @DisplayName("No accept input fail")
    void testInvalidID_NoEngAndNum() {
        String invalidID = "abc12@";
        assertFalse(validator.isValid(invalidID, null));
    }

    @Test
    @DisplayName("blank input fail")
    void testInvalidID_blank() {
        String invalidID = "";
        assertFalse(validator.isValid(invalidID, null));
    }
}

