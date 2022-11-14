package com.ckt.ecommercecybersoft.user.controller;

import com.ckt.ecommercecybersoft.common.model.ResponseDTO;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.common.utils.ResponseUtils;
import com.ckt.ecommercecybersoft.order.dto.ResponseOrderDTO;
import com.ckt.ecommercecybersoft.post.dto.PostDTO;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.security.authorization.AdminOnly;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.request.PasswordModel;
import com.ckt.ecommercecybersoft.user.model.request.PasswordResetRequestModel;
import com.ckt.ecommercecybersoft.user.model.request.UserInfoModel;
import com.ckt.ecommercecybersoft.user.model.request.UserSignUpModel;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = UserUrlUtils.USER_API_V1)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectMapper mapper;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    /**
     * Get all users with pagination
     * @param pageSize page size
     * @param pageNumber page number
     * @return list of users
     */
    @AdminOnly
    @GetMapping
    public ResponseEntity<ResponseDTO> getAllUsers(@RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Get all users at page {}  with page size {} ", pageNumber, pageSize);
        List<UserDto> users = userService.findAllDto(Pageable.ofSize(pageSize).withPage(pageNumber - 1), UserDto.class);
        return ResponseUtils.get(users, HttpStatus.OK);
    }

    /**
     * Get user by id
     * @param id user id, must be UUID
     * @return user
     */
    @AdminOnly
    @GetMapping(path = UserUrlUtils.BY_ID)
    public ResponseEntity<ResponseDTO> getUserById(@PathVariable UUID id) {
        logger.info("Get user by id: {}", id);
        UserDto user = userService.findUserById(id);
        UserResponseModel userResponseModel = mapper.map(user, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }


    /**
     * Get current user info by token in header
     * @return user
     */
    @GetMapping(path = UserUrlUtils.CURRENT_LOGIN_USER)
    public ResponseEntity<ResponseDTO> getCurrentUser() {
        logger.info("Get current user");
        UserDto userDto = userService.getCurrentUser();
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }

    /**
     * Update user basic information. Only admin can update other user's information
     * Just update info of current login user
     * @param user user info model, user id is not required
     * @return updated user
     */
    @PutMapping
    public ResponseEntity<ResponseDTO> updateUserInfo(@Valid @RequestBody UserInfoModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        UserDto currentLoginUser = userService.getCurrentUser();
        userDto.setId(currentLoginUser.getId());
        userDto.setUsername(currentLoginUser.getUsername());
        logger.info("Update user id: {}. \n New user info: {}", userDto.getId(), userDto);
        userDto = userService.updateUser(userDto);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }

    /**
     * Change password of current login user
     * @param passwordModel password model with old password, new password
     * @return operation status
     */
    @PutMapping(path = UserUrlUtils.CHANGE_PASSWORD)
    public ResponseEntity<ResponseDTO> changePassword(@Valid @RequestBody PasswordModel passwordModel) {
        UserDto currentLoginUser = userService.getCurrentUser();
        passwordModel.setId(currentLoginUser.getId());
        logger.info("Change password for user id: {}", passwordModel.getId());
        boolean isChanged = userService.changePassword(passwordModel.getId(), passwordModel.getOldPassword(), passwordModel.getPassword());
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.CHANGE_PASSWORD.name());
        if (isChanged) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.name());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.name());
        }
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    /**
     * Delete user by id, only admin can delete user
     * @param id user id must be UUID
     * @return operation status
     */
    @AdminOnly
    @DeleteMapping(path = UserUrlUtils.BY_ID)
    public ResponseEntity<ResponseDTO> deleteUser(@PathVariable UUID id) {
        logger.info("Delete user id: {} ", id);
        boolean isDeleted = userService.deleteUser(id);
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationName(OperationName.DELETE.name());
        if (isDeleted) {
            operationStatusModel.setOperationResult(OperationStatus.SUCCESS.name());
        } else {
            operationStatusModel.setOperationResult(OperationStatus.ERROR.name());
        }
        return ResponseUtils.get(operationStatusModel, HttpStatus.OK);
    }

    /**
     * Sign up new user
     * @param user user sign up model with username, password, email, name, avatar url, address
     * @return created user but not active, need to verify email
     */
    @PostMapping(path = UserUrlUtils.SIGN_UP)
    public ResponseEntity<ResponseDTO> signUp(@Valid @RequestBody UserSignUpModel user) {
        UserDto userDto = mapper.map(user, UserDto.class);
        logger.info("Sign up user: {}", userDto);
        UserDto createdUser = userService.createUser(userDto);
        logger.info("Created user: {}", createdUser);
        UserResponseModel userResponseModel = mapper.map(createdUser, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.CREATED);
    }

    /**
     * Verify email after sign up, if email is verified, user will be active
     * @param token token from email
     * @return operation status
     */
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

    /**
     * Request to reset password, send email to user
     * @param requestModel email of user
     * @return operation status
     */
    @PostMapping(path = UserUrlUtils.PASSWORD_RESET_REQUEST)
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

    /**
     * Verify token to reset password
     * @param token token from email
     * @return operation status
     */
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

    /**
     * Reset password
     * @param passwordModel password reset model include new password;
     * @param token token from email
     * @return operation status
     */
    @PostMapping(path = UserUrlUtils.RESET_PASSWORD)
    public ResponseEntity<ResponseDTO> resetPassword(@RequestParam String token, @Valid @RequestBody PasswordModel passwordModel) {
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

    /**
     * Admin can change user role
     * @param id user id must be UUID
     * @param roleDto role model, just need role code. Role must be exist
     * @return user
     */
    @AdminOnly
    @PutMapping(path = UserUrlUtils.CHANGE_ROLE)
    public ResponseEntity<ResponseDTO> changeRole(@PathVariable UUID id, @RequestBody RoleDto roleDto) {
        logger.info("Change role for user id: {} to role: {}", id, roleDto.getCode());
        UserDto userDto = userService.changeRole(id, roleDto);
        UserResponseModel userResponseModel = mapper.map(userDto, UserResponseModel.class);
        return ResponseUtils.get(userResponseModel, HttpStatus.OK);
    }

    @GetMapping(path = UserUrlUtils.SEARCH_USER)
    public ResponseEntity<ResponseDTO> searchUser(@RequestParam String keyword, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Search user with keyword: {}", keyword);
        List<UserDto> userDtoPage = userService.searchUser(keyword, Pageable.ofSize(pageSize).withPage(pageNumber-1));
        if (userDtoPage == null) {
            return ResponseUtils.get(null, HttpStatus.OK);
        }
        List<UserResponseModel> userResponseModelList = userDtoPage.stream().map(userDto -> mapper.map(userDto, UserResponseModel.class)).collect(Collectors.toList());
        return ResponseUtils.get(userResponseModelList, HttpStatus.OK);
    }

    @GetMapping(path = UserUrlUtils.GET_ORDERS)
    public ResponseEntity<ResponseDTO> getOrders(@PathVariable UUID id, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Get orders of user id: {}", id);
        //if user is not admin, only get orders of current user
        UserDto currentUser = userService.getCurrentUser();
        if (!currentUser.getRole().getCode().equals("ADMIN")) {
            id = currentUser.getId();
        }
        List<ResponseOrderDTO> orderDtoPage = userService.findUserById(id).getOrders().subList((pageNumber-1)*pageSize, pageNumber*pageSize);
        return ResponseUtils.get(orderDtoPage, HttpStatus.OK);
    }

    @GetMapping(path = UserUrlUtils.GET_POSTS)
    public ResponseEntity<ResponseDTO> getPosts(@PathVariable UUID id, @RequestParam(defaultValue = "5") int pageSize, @RequestParam(defaultValue = "1") int pageNumber) {
        logger.info("Get posts of user id: {}", id);
        List<PostDTO> postDtoPage = userService.findUserById(id).getPosts().subList((pageNumber-1)*pageSize, pageNumber*pageSize);
        return ResponseUtils.get(postDtoPage, HttpStatus.OK);
    }

}
