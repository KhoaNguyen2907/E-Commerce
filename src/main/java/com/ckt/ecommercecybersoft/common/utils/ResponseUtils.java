package com.ckt.ecommercecybersoft.common.utils;

import com.ckt.ecommercecybersoft.common.exception.ForbiddenException;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.Error;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import lombok.experimental.UtilityClass;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Response Utils class to handle response
 *
 * @author KhoaNguyen
 * @version 1.0
 * @since 2022-10-27
 */
@UtilityClass
public class ResponseUtils {

    public static ResponseEntity<ResponseDTO> get(Object data, HttpStatus status) {
//        ResponseDTO response = ResponseDTO.builder()
//                .content(data)
//                .hasErrors(false)
//                .errors(null)
//                .timestamp(DateTimeUtils.now())
//                .status(status.value())
//                .build();

        ResponseDTO response = getResponseDto(data, status, null);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> errorConstraint(ConstraintViolationException exception, HttpStatus status) {
        List<String> errors = exception.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage).collect(Collectors.toList());
        List<Error> errorList = getErrorList(errors);
        ResponseDTO response = getResponseDto(null, status, errorList);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> errorMethodArgument(MethodArgumentNotValidException exception, HttpStatus status) {
        List<String> errors = exception.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.toList());
        List<Error> errorList = getErrorList(errors);
        ResponseDTO response = getResponseDto(null, status, errorList);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> errorNotFound(NotFoundException exception, HttpStatus status) {
        String error = exception.getMessage();
        List<Error> errorList = getErrorList(List.of(error));
        ResponseDTO response = getResponseDto(null, status, errorList);

        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> errorDisabled(DisabledException exception, HttpStatus status) {
        String message = exception.getMessage();
        List<Error> errorList = List.of(new Error(3,message));
        ResponseDTO response = getResponseDto(null, status, errorList);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> errorBadCredentials(BadCredentialsException exception, HttpStatus status) {
        String message = "Wrong password";
        List<Error> errorList = List.of(new Error(4,message));
        ResponseDTO response = getResponseDto(null, status, errorList);
        return new ResponseEntity<>(response, status);
    }

    //When user login with wrong username, it will throw AuthenticationException instead of NotFoundException.
    public static ResponseEntity<ResponseDTO> errorUnauthorized(AuthenticationException exception, HttpStatus status) {
        String error = exception.getMessage();

        String message = "Please login";

        int errorCode = 1;
        if (error.startsWith("00")){
            errorCode = getErrorCode(error);
            message = getErrorMessage(error);
        }
        List<Error> errorList = List.of(new Error(errorCode, message));
        ResponseDTO response = getResponseDto(null, status, errorList);
        return new ResponseEntity<>(response, status);
    }

    public static ResponseEntity<ResponseDTO> errorForbidden(ForbiddenException exception, HttpStatus status) {
        String message = "Don't have permission to access this resource";
        List<Error> errorList = List.of(new Error(2,message));
        ResponseDTO response = getResponseDto(null, status, errorList);

        return new ResponseEntity<>(response, status);
    }

    private int getErrorCode(String error) {
        return Integer.parseInt(error.substring(0, 3));
    }

    private String getErrorMessage(String error) {
        return error.substring(4);
    }

    private List<Error> getErrorList(List<String> errors) {
        return errors.stream().map(e -> new Error(getErrorCode(e), getErrorMessage(e))).collect(Collectors.toList());
    }

    private ResponseDTO getResponseDto(Object data, HttpStatus status, List<Error> errorList) {
        boolean hasError = true;
        if (errorList == null){
            hasError = false;
        }
        boolean isContentArray = data != null && data.toString().charAt(0) == '[';

        return ResponseDTO.builder().content(data).hasErrors(hasError).errors(errorList)
                .timestamp(DateTimeUtils.now()).status(status.value()).contentArray(isContentArray).build();
    }
}
