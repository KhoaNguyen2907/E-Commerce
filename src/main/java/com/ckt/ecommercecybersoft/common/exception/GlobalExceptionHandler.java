package com.ckt.ecommercecybersoft.common.exception;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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
    public ResponseEntity<ResponseDTO> handleGlobalException(RuntimeException exception
    ) {
        return ResponseUtils.error(exception, HttpStatus.BAD_REQUEST);
    }
}
