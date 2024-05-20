package com.softgallery.issuemanagementbackEnd.custom_annotation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.Pattern;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {})
@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{4,8}$")
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
public @interface IDRule {
    String message() default "한글과 숫자를 조합한 4-8자리 아이디를 입력해 주세요";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}


