package com.ckt.ecommercecybersoft.common.utils;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.model.Error;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Response Utils class to handle response
 *
 * @author KhoaNguyen
 * @version 1.0
 * @since 2022-10-27
 */

public class ResponseUtils {

    public static ResponseEntity<ResponseDTO> get(Object data, HttpStatus status) {
        ResponseDTO response = ResponseDTO.builder()
                .content(data)
                .errors(null)
                .timestamp(DateTimeUtils.now())
                .status(status.value())
                .isContentArray(false)
                .build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> get(List<Object> data, HttpStatus status) {
        ResponseDTO response = ResponseDTO.builder()
                .content(data)
                .errors(null)
                .timestamp(DateTimeUtils.now())
                .status(status.value())
                .isContentArray(true)
                .build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(ConstraintViolationException exception, HttpStatus status) {

        List<Error> errors = exception.getConstraintViolations().stream()
                .map(constraintViolation ->
                        new Error(0, constraintViolation.getMessage()))
                .collect(Collectors.toList());
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true)
                .errors(List.of(new Error(0, exception.getMessage())))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(MethodArgumentNotValidException exception, HttpStatus status) {
        List<Error> errors = exception.getAllErrors().stream()
                .map(e -> new Error(0, e.getDefaultMessage()))
                .collect(Collectors.toList());
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true)
                .errors(List.of(new Error(0, exception.getMessage())))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(NotFoundException exception, HttpStatus status) {
        String error = exception.getMessage();
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true)
                .errors(List.of(new Error(0, error)))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(DisabledException exception, HttpStatus status) {
        String error = exception.getMessage();
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true)
                .errors(List.of(new Error(0, error)))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(BadCredentialsException exception, HttpStatus status) {
        String error = "Wrong username or password";
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true)
                .errors(List.of(new Error(0, error)))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }


}
