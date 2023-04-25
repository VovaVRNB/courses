package com.courses.controller.exception;

import com.courses.dto.HttpErrorResponseDto;
import com.courses.exception.NotFoundException;
import com.courses.exception.UserAlreadyExistException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionTranslator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionTranslator.class);

    @ResponseBody
    @ExceptionHandler(UserAlreadyExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponseDto handleException(UserAlreadyExistException e) {
        LOGGER.error("Handled UserAlreadyExistException: ", e);
        return new HttpErrorResponseDto(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponseDto handleException(UsernameNotFoundException e) {
        LOGGER.error("Handled UsernameNotFoundException: ", e);
        return new HttpErrorResponseDto(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpErrorResponseDto handleException(NotFoundException e) {
        LOGGER.error("Handled NotFoundException: ", e);
        return new HttpErrorResponseDto(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleException(ConstraintViolationException e) {
        LOGGER.error("Handled ConstraintViolationException: ", e);
        return e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());
    }

}