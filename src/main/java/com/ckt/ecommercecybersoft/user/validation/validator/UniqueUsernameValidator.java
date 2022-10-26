package com.ckt.ecommercecybersoft.user.validation.validator;

import com.ckt.ecommercecybersoft.user.validation.annotation.UniqueUsername;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    @Autowired
    private UserRepository userRepository;

    private String message;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return true;
        }
        if (!user.get().isEmailVerified()) {
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation().disableDefaultConstraintViolation();
        return false;
    }
}