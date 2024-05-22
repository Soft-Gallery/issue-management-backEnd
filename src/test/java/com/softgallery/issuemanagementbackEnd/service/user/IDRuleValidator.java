package com.softgallery.issuemanagementbackEnd.service.user;

import com.softgallery.issuemanagementbackEnd.custom_annotation.IDRule;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class IDRuleValidator implements ConstraintValidator<IDRule, String> {

    @Override
    public void initialize(IDRule constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        return value.matches("^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{4,8}$");
    }
}

