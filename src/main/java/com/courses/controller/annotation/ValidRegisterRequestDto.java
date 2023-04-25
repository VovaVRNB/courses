package com.courses.controller.annotation;

import com.courses.controller.validator.RegisterRequestDtoValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegisterRequestDtoValidator.class)
public @interface ValidRegisterRequestDto {
    String message () default "Fields is empty";
    Class<?>[] groups () default {};
    Class<? extends Payload>[] payload () default {};
}
