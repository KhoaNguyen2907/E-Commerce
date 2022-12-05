package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.exception.BadRequestException;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.utils.ExceptionUtils;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.role.dto.RoleDto;
import com.ckt.ecommercecybersoft.role.model.Role;
import com.ckt.ecommercecybersoft.role.repository.RoleRepository;
import com.ckt.ecommercecybersoft.role.utils.RoleExceptionUtils;
import com.ckt.ecommercecybersoft.security.jwt.JwtUtils;
import com.ckt.ecommercecybersoft.security.utils.SecurityUtils;
import com.ckt.ecommercecybersoft.user.controller.UserController;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.dto.UserDtoWithCart;
import com.ckt.ecommercecybersoft.user.dto.UserDtoWithOrders;
import com.ckt.ecommercecybersoft.user.dto.UserDtoWithPosts;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import com.ckt.ecommercecybersoft.user.utils.MailUtils;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * User service implementation
 * @author KhoaNguyen
 */
@Service
@Transactional
public class UserServiceImpl implements UserService, Serializable {

    private Logger logger =LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserCaching userCaching;

    private final UserRepository userRepository;
    private final ProjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final RoleRepository roleRepository;

    @Value("${fe.host}")
    private String feHost;


    public UserServiceImpl(EmailService emailService, UserRepository userRepository, ProjectMapper mapper, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.roleRepository = roleRepository;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return userRepository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }

    /**
     * Check if the user exists in the database, if not, create a new user.
     * If the user exists, update that user with the new information by using the id.
     * User created will have the role of "USER" by default.
     * User needs to verify their email address before they can login.
     */
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = getMapper().map(userDto, User.class);
        User userByUsername = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (userByUsername != null) {
            user.setId(userByUsername.getId());
            logger.info("This user already exists but not verified, updating the user with the new information");
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmailVerified(false);
        user.setStatus(User.Status.ACTIVE);
        Role userRole = roleRepository.findByCode("USER").orElseThrow(() -> new NotFoundException(RoleExceptionUtils.ROLE_NOT_FOUND));
        user.setRole(userRole);
        user = userRepository.save(user);
        String token = JwtUtils.generateEmailToken(user.getUsername());
        emailService.sendEmail(user.getEmail(), MailUtils.EMAIL_VERIFICATION_SUBJECT, MailUtils.EMAIL_VERIFICATION_BODY + feHost+MailUtils.EMAIL_VERIFICATION_URL + token);
        logger.info("User created successfully, email verification sent to the user");
        return getMapper().map(user, UserDto.class);
    }
    /**
     * User can update their information except for their username, role and email.
     */
    @Override
    public UserDto updateUser(UserDto userDto) {
        User newUser = getMapper().map(userDto, User.class);
        User updateUser = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        updateUser.setAvatar(newUser.getAvatar());
        updateUser.setName(newUser.getName());
        updateUser.setEmail(newUser.getEmail());
        updateUser.setAddress(newUser.getAddress());
        userRepository.flush();
        userCaching.updateUserCache(updateUser);
        return getMapper().map(newUser, UserDto.class);
    }

    @Override
    public boolean deleteUser(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        user.setStatus(User.Status.PERMANENTLY_BLOCKED);
        userRepository.save(user);
        userRepository.flush();
        //remove user from cache by both id and username
        userCaching.deleteUserCache(user);
        return true;
    }

    /**
     * Find user by username
     */
    @Override
    @Cacheable(value = "users", key = "#username")
    public UserDto findByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        return getMapper().map(user, UserDto.class);
    }

    /**
     * Find user by id
     */
    @Override
    @Cacheable(value = "users", key = "#id")
    public UserDto findUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        return getMapper().map(user, UserDto.class);
    }

    /**
     * Verify activation token and activate user. If token is invalid or expired, throw exception.
     */
    @Override
    public boolean verifyEmailToken(String token) {
        if (JwtUtils.validateJwt(token)) {
            if (JwtUtils.hasTokenExpired(token)) {
                logger.error("Token has expired");
                throw new NotFoundException(ExceptionUtils.EXPIRED_TOKEN);
            }
            String username = JwtUtils.getUsernameFromJwt(token);
            User user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            user.setEmailVerified(true);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    /**
     * This method wil change role of user. Throw exception if user or is not found
     */
    @Override
    public UserDto changeRole(UUID id, RoleDto roleDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));

        Role role = roleRepository.findByCode(roleDto.getCode())
                .orElseThrow(() -> new NotFoundException(RoleExceptionUtils.ROLE_NOT_FOUND));

        user.setRole(role);
        user = userRepository.save(user);
        userCaching.deleteUserCache(user);
        return getMapper().map(user, UserDto.class);
    }

    @Override
    public boolean changePassword(UUID id, String oldPassword, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        if (passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            user = userRepository.save(user);
            userCaching.deleteUserCache(user);
            return true;
        } else {
            throw new BadRequestException(UserExceptionUtils.INCORRECT_PASSWORD);
        }
    }

    @Override
    public Optional<UserDto> getCurrentUser() throws NotFoundException {
        String username = SecurityUtils.getLoginUsername().orElse(null);
        if (username != null && !username.equals("anonymousUser")) {
            User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            return Optional.ofNullable(getMapper().map(user, UserDto.class));
        }
        return Optional.empty();
    }

    @Override
    public List<UserDto> searchUser(String keyword, Pageable pageable) {
        List<User> users = userRepository.findUserByKeyword(keyword.toLowerCase(), pageable);
        if (users.isEmpty()) {
            return null;
        }
        return users.stream().map(user -> getMapper().map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserDtoWithCart getUserWithCart(UUID id) {
        User user = userRepository.findUserWithCartById(id).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        return getMapper().map(user, UserDtoWithCart.class);
    }

    @Override
    public UserDtoWithPosts getUserWithPosts(UUID id) {
        User user = userRepository.findUserWithPostsById(id).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        return getMapper().map(user, UserDtoWithPosts.class);
    }

    @Override
    public UserDtoWithOrders getUserWithOrders(UUID id) {
        User user = userRepository.findUserWithOrdersById(id).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        return getMapper().map(user, UserDtoWithOrders.class);
    }

    /**
     * Receives an email and sends a password reset link to the user. If the user is not found, it throws an exception.
     */
    @Override
    public boolean requestPasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        if (user != null) {
//            user.setPasswordResetToken(JwtUtils.generateToken(email));
//            user = repository.save(user);
//            emailService.sendEmail(user.getEmail(), "Password Reset", "Please click on the link below to reset your password: http://localhost:8080/api/v1/users/password-reset?token=" + user.getPasswordResetToken());

            String token = JwtUtils.generateEmailToken(user.getUsername());
            emailService.sendEmail(user.getEmail(), MailUtils.PASSWORD_RESET_REQUEST_SUBJECT, MailUtils.PASSWORD_RESET_REQUEST_BODY + feHost+MailUtils.PASSWORD_RESET_URL + token);
            logger.info("Password reset request sent to the user's email");
            return true;
        }
        return false;
    }

    /**
     * Pass in the token, this method will check if the token is valid and not expired
     */
    @Override
    public boolean verifyPasswordResetToken(String token) {
        if (JwtUtils.validateJwt(token)) {
            User user = userRepository.findByUsername(JwtUtils.getUsernameFromJwt(token))
                    .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            if (user != null && !JwtUtils.hasTokenExpired(token)) {
                return true;
            } else {
                logger.info("Token has expired");
                throw new NotFoundException(ExceptionUtils.EXPIRED_TOKEN);
            }
        }
        logger.info("Token is invalid");
        return false;
    }

    /**
     * This method will validate the token and change the password of the user.
     * The token has to be valid and not expired.
     */
    @Override
    public UserDto resetPassword(String token, String password) {
        if (JwtUtils.validateJwt(token)) {
            User user = userRepository.findByUsername(JwtUtils.getUsernameFromJwt(token))
                    .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            if (user != null && !JwtUtils.hasTokenExpired(token)) {
                user.setPassword(passwordEncoder.encode(password));
                user = userRepository.save(user);
                emailService.sendEmail(user.getEmail(), MailUtils.PASSWORD_RESET_SUBJECT, MailUtils.PASSWORD_RESET_BODY);
                userRepository.flush();
                UserDto retUserDto = getMapper().map(user, UserDto.class);
                userCaching.deleteUserCache(user);
                return retUserDto;
            } else {
                logger.error("Token has expired");
                throw new NotFoundException(ExceptionUtils.EXPIRED_TOKEN);
            }
        }
        return null;
    }



}
