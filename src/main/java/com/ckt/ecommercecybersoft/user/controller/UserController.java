package com.ckt.ecommercecybersoft.user.controller;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.security.authorization.AdminOnly;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.model.request.PasswordModel;
import com.ckt.ecommercecybersoft.user.model.request.PasswordResetRequestModel;
import com.ckt.ecommercecybersoft.user.model.request.UserRequestModel;
import com.ckt.ecommercecybersoft.user.model.response.OperationName;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatus;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatusModel;
import com.ckt.ecommercecybersoft.user.model.response.UserResponseModel;
import com.ckt.ecommercecybersoft.user.service.UserService;
import com.ckt.ecommercecybersoft.user.utils.UserUrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = UserUrlUtils.USER_API_V1)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectMapper mapper;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @AdminOnly
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Get all users at page {}  with page size {} ", pageNumber, pageSize);
        List<UserDto> users =  userService.findAllDto(Pageable.ofSize(pageSize).withPage(pageNumber-1), UserDto.class);
        return ResponseUtils.get(users, HttpStatus.OK);
    }

    @AdminOnly
    @GetMapping(path = UserUrlUtils.BY_ID)
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable UUID id) {
        logger.info("Get user by id: {}", id);
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return ResponseUtils.get(mapper.map(user, UserDto.class), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestBody UserRequestModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        logger.info("Update user id: {}. \n New user info: {}", userDto.getId(),userDto);
        userDto = userService.updateUser(userDto);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }

    @AdminOnly
    @DeleteMapping(path = UserUrlUtils.BY_ID)
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable UUID id) {
        logger.info("Delete user id: {} ", id);
        userService.deleteById(id);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.DELETE.name());
        operationStatusModel.setOperationResult(OperationStatus.SUCCESS.name());
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @PostMapping(path = UserUrlUtils.SIGN_UP)
    public ResponseEntity<ResponseDTO> signUp(@Valid @RequestBody UserRequestModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        logger.info("Sign up user: {}", userDto);
        UserDto createdUser = userService.createUser(userDto);
        logger.info("Created user: {}", createdUser);
        UserResponseModel userResponseModel = mapper.map(createdUser, UserResponseModel.class);
       return ResponseUtils.get(userResponseModel, HttpStatus.CREATED);
    }

    @GetMapping(path = UserUrlUtils.EMAIL_VERIFICATION)
    public ResponseEntity<ResponseDTO> verifyEmail(@RequestParam String token) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.VERIFY_EMAIL.toString());
        boolean isVerified = userService.verifyEmailToken(token);
        logger.info("Email verification status: {}", isVerified);
        if (isVerified) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @PostMapping (path = UserUrlUtils.PASSWORD_RESET_REQUEST)
    public ResponseEntity<ResponseDTO> requestResetPassword(@Valid @RequestBody PasswordResetRequestModel requestModel) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.REQUEST_PASSWORD_RESET.toString());
        boolean operationResult = userService.requestPasswordReset(requestModel.getEmail());
        logger.info("Password reset request status: {}", operationResult);
        if (operationResult) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @GetMapping(path = UserUrlUtils.VERIFY_PASSWORD_RESET_TOKEN)
    public ResponseEntity<ResponseDTO> verifyPasswordResetToken(@RequestParam String token) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.VERIFY_PASSWORD_RESET_TOKEN.toString());
        boolean operationResult = userService.verifyPasswordResetToken(token);
        logger.info("Password reset token verification status: {}", operationResult);
        if (operationResult) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @PostMapping (path = UserUrlUtils.RESET_PASSWORD)
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token,@Valid  @RequestBody PasswordModel passwordModel) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.PASSWORD_RESET.toString());
        logger.info("New password: {}", passwordModel.getPassword());
        boolean operationResult = (userService.resetPassword(token, passwordModel.getPassword()) != null);
        logger.info("Password reset status: {}", operationResult);
        if (operationResult) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    // Just need role code
    @AdminOnly
    @PutMapping(path = UserUrlUtils.CHANGE_ROLE)
    public ResponseEntity<ResponseDTO> changeRole(@PathVariable UUID id, @RequestBody RoleDto roleDto) {
        logger.info("Change role for user id: {} to role: {}", id, roleDto.getCode());
        UserDto userDto = userService.changeRole(id, roleDto);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }

}
