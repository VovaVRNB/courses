package com.courses.controller.validator;

import com.courses.controller.annotation.ValidRegisterRequestDto;
import com.courses.dto.RegisterRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RegisterRequestDtoValidator implements ConstraintValidator<ValidRegisterRequestDto, RegisterRequestDto> {

    @Override
    public boolean isValid(RegisterRequestDto registerRequestDto, ConstraintValidatorContext constraintValidatorContext) {
        constraintValidatorContext.disableDefaultConstraintViolation();
        boolean isValid = true;
        String pattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

        if (registerRequestDto.getFirstName().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("First name shouldn't be empty").addConstraintViolation();
            isValid = false;
        }
        if (registerRequestDto.getLastName().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Last name shouldn't be empty").addConstraintViolation();
            isValid = false;
        }
        if (!registerRequestDto.getEmail().matches(pattern)) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Invalid email").addConstraintViolation();
            isValid = false;
        }
        if (registerRequestDto.getPassword().isEmpty()) {
            constraintValidatorContext.buildConstraintViolationWithTemplate("Password shouldn't be empty").addConstraintViolation();
            isValid = false;
        }

        return isValid;
    }
}
