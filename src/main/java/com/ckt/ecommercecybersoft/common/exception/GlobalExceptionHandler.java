package com.ckt.ecommercecybersoft.common.exception;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import javax.validation.ValidationException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        return ResponseUtils.error(exception, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        return ResponseUtils.error(exception, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException exception) {
        return ResponseUtils.error(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(DisabledException exception) {
        return ResponseUtils.error(exception, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleException(BadCredentialsException exception) {
        return ResponseUtils.error(exception, HttpStatus.FORBIDDEN);
    }

}
