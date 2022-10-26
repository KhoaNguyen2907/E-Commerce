package com.ckt.ecommercecybersoft.user.controller;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.model.request.PasswordModel;
import com.ckt.ecommercecybersoft.user.model.request.PasswordResetRequestModel;
import com.ckt.ecommercecybersoft.user.model.request.UserRequestModel;
import com.ckt.ecommercecybersoft.user.model.response.OperationName;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatus;
import com.ckt.ecommercecybersoft.user.model.response.OperationStatusModel;
import com.ckt.ecommercecybersoft.user.model.response.UserResponseModel;
import com.ckt.ecommercecybersoft.user.service.EmailService;
import com.ckt.ecommercecybersoft.user.service.UserService;
import com.ckt.ecommercecybersoft.user.utils.UrlUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = UrlUtils.USER_API_V1)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectMapper mapper;

    @GetMapping
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        List<UserDto> users =  userService.findAllDto(Pageable.ofSize(pageSize).withPage(pageNumber-1), UserDto.class);
        return ResponseUtils.get(users, HttpStatus.OK);
    }

    @GetMapping(path = UrlUtils.BY_ID)
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable UUID id) {
        User user = userService.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return ResponseUtils.get(mapper.map(user, UserDto.class), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateUser(@Valid @RequestBody UserRequestModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        userDto = userService.saveOrUpdate(userDto, User.class);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }

    @DeleteMapping(path = UrlUtils.BY_ID)
    public ResponseEntity<UserDto> deleteUser(@PathVariable UUID id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(path = UrlUtils.SIGN_UP)
    public ResponseEntity<ResponseDTO> signUp(@Valid @RequestBody UserRequestModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        UserResponseModel userResponseModel = mapper.map(createdUser, UserResponseModel.class);
       return ResponseUtils.get(userResponseModel, HttpStatus.CREATED);
    }

    @GetMapping(path = UrlUtils.EMAIL_VERIFICATION)
    public ResponseEntity<ResponseDTO> verifyEmail(@RequestParam String token) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.VERIFY_EMAIL.toString());
        boolean isVerified = userService.verifyEmailToken(token);

        if (isVerified) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }

        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @PostMapping (path = UrlUtils.PASSWORD_RESET_REQUEST)
    public ResponseEntity<ResponseDTO> requestResetPassword(@Valid @RequestBody PasswordResetRequestModel requestModel) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.REQUEST_PASSWORD_RESET.toString());

        boolean operationResult = userService.requestPasswordReset(requestModel.getEmail());

        if (operationResult) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }

        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @GetMapping(path = UrlUtils.VERIFY_PASSWORD_RESET_TOKEN)
    public ResponseEntity<ResponseDTO> verifyPasswordResetToken(@RequestParam String token) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.VERIFY_PASSWORD_RESET_TOKEN.toString());

        boolean operationResult = userService.verifyPasswordResetToken(token);

        if (operationResult) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }

        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    @PostMapping (path = UrlUtils.RESET_PASSWORD)
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token,@Valid  @RequestBody PasswordModel passwordModel) {
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.PASSWORD_RESET.toString());

        boolean operationResult = (userService.resetPassword(token, passwordModel.getPassword()) != null);

        if (operationResult) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.toString());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.toString());
        }

        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

}
