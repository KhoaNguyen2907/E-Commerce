package com.ckt.ecommercecybersoft.common.utils;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.stream.Collectors;

public class ResponseUtils {

    public static ResponseEntity<ResponseDTO> error(ConstraintViolationException exception, HttpStatus status) {

        List<String> errors = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true).errors(errors)
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(MethodArgumentNotValidException exception, HttpStatus status) {
        List<String> errors = exception.getAllErrors().stream()
                .map(e -> e.getDefaultMessage()).collect(Collectors.toList());
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true).errors(errors)
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(NotFoundException exception, HttpStatus status) {

        String error = exception.getMessage();
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true).errors(List.of(error))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> error(RuntimeException exception, HttpStatus status) {
        String error = "Something went wrong. That's all we know.";
        ResponseDTO response = ResponseDTO.builder().content(null).hasErrors(true).errors(List.of(error))
                .timestamp(DateTimeUtils.now()).status(status.value()).build();
        return new ResponseEntity<>(response, status);
    }

}
