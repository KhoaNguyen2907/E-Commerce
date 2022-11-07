package com.ckt.ecommercecybersoft.common.exception;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleConstraintViolationException(ConstraintViolationException exception) {
        logger.error("Constraint Violation Exception: \n {} ", exception.getMessage());
        return ResponseUtils.errorConstraint(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        logger.error("Method Argument Not Valid Exception: \n {} ", exception.getMessage());
        return ResponseUtils.errorMethodArgument(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseDTO> handleNotFoundException(NotFoundException exception) {
        logger.error("Not Found Exception \n {}", exception.getMessage());
        return ResponseUtils.errorNotFound(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleDisableException(DisabledException exception) {
        logger.error("Disabled Exception: User is not active \n {}", exception.getMessage());
        return ResponseUtils.errorDisabled(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleBadCredentialsException(BadCredentialsException exception) {
        logger.error("Bad Credentials Exception: Wrong password \n {}", exception.getMessage());
        return ResponseUtils.errorBadCredentials(exception, HttpStatus.UNAUTHORIZED);
    }

    /**
     * When user don't have permission to access resource, this method will be called
     */
    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleForbiddenException(ForbiddenException exception) {
        logger.error("Forbidden Exception: Not permission \n {}", exception.getMessage());
        return ResponseUtils.errorForbidden(exception, HttpStatus.FORBIDDEN);
    }

    /**
     * When user is not login and try to access to the authenticated resource
     * When user login with wrong username, this method will also be called. Because maybe loadUserByUsername will throw Authentication Exception on priority over other exceptions.
     * Handle AuthenticationException from Spring Security Authentication Entry Point
     */
    @ExceptionHandler
    public ResponseEntity<ResponseDTO> handleAuthenticationException(AuthenticationException exception) {
        logger.error("Authentication Exception \n {} ", exception.getMessage());
        return ResponseUtils.errorUnauthorized(exception, HttpStatus.UNAUTHORIZED);
    }
}
