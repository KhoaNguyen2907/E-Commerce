package com.ckt.ecommercecybersoft.user.service;

import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.common.utils.ExceptionUtils;
import com.ckt.ecommercecybersoft.common.utils.ProjectMapper;
import com.ckt.ecommercecybersoft.security.jwt.JwtUtils;
import com.ckt.ecommercecybersoft.user.dto.UserDto;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import com.ckt.ecommercecybersoft.user.utils.MailUtils;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ProjectMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;


    public UserServiceImpl(EmailService emailService, UserRepository repository, ProjectMapper mapper, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Override
    public JpaRepository<User, UUID> getRepository() {
        return repository;
    }

    @Override
    public ProjectMapper getMapper() {
        return mapper;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);

        User userByUsername = repository.findByUsername(user.getUsername()).orElse(null);
        if (userByUsername != null) {
            user.setId(userByUsername.getId());
        }
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmailVerified(false);
        user = repository.save(user);
        String token = JwtUtils.generateEmailToken(user.getUsername());

        emailService.sendEmail(user.getEmail(), MailUtils.EMAIL_VERIFICATION_SUBJECT, MailUtils.EMAIL_VERIFICATION_BODY + MailUtils.EMAIL_VERIFICATION_URL + token);

        return mapper.map(user, UserDto.class);
    }

    @Override
    public UserDto findByUsername(String username) {
        return mapper.map(
                repository.findByUsername(username)
                        .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND)), UserDto.class);
    }

    @Override
    public boolean verifyEmailToken(String token) {
        if (JwtUtils.validateJwt(token)) {
            if (JwtUtils.hasTokenExpired(token)) {
               throw new NotFoundException(ExceptionUtils.EXPIRED_TOKEN);
            }
            String username = JwtUtils.getUsernameFromJwt(token);
            User user = repository.findByUsername(username)
                    .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            user.setEmailVerified(true);
            repository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean requestPasswordReset(String email) {
        User user = repository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        if (user != null) {
//            user.setPasswordResetToken(JwtUtils.generateToken(email));
//            user = repository.save(user);
//            emailService.sendEmail(user.getEmail(), "Password Reset", "Please click on the link below to reset your password: http://localhost:8080/api/v1/users/password-reset?token=" + user.getPasswordResetToken());

            String token = JwtUtils.generateEmailToken(user.getUsername());
            emailService.sendEmail(user.getEmail(), MailUtils.PASSWORD_RESET_REQUEST_SUBJECT, MailUtils.PASSWORD_RESET_REQUEST_BODY+ MailUtils.PASSWORD_RESET_URL + token);
            return true;
        }
        return false;
    }


    @Override
    public boolean verifyPasswordResetToken(String token) {
        if (JwtUtils.validateJwt(token)) {
            User user = repository.findByUsername(JwtUtils.getUsernameFromJwt(token))
                    .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            if (user != null && !JwtUtils.hasTokenExpired(token)) {
                return true;
            } else {
                throw new NotFoundException(ExceptionUtils.EXPIRED_TOKEN);
            }
        }
        return false;
    }

    @Override
    public UserDto resetPassword(String token, String password) {
        if (JwtUtils.validateJwt(token)) {
            User user = repository.findByUsername(JwtUtils.getUsernameFromJwt(token))
                    .orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
            if (user != null && !JwtUtils.hasTokenExpired(token)) {
                user.setPassword(passwordEncoder.encode(password));
                user = repository.save(user);
                emailService.sendEmail(user.getEmail(), MailUtils.PASSWORD_RESET_SUBJECT, MailUtils.PASSWORD_RESET_BODY);
                return mapper.map(user, UserDto.class);
            } else {
                throw new NotFoundException(ExceptionUtils.EXPIRED_TOKEN);
            }
        }
        return null;
    }
}
