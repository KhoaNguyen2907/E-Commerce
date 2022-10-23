package com.ckt.ecommercecybersoft.user.validation.validator;

import com.ckt.ecommercecybersoft.user.validation.annotation.UniqueEmail;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private UserRepository userRepository;

    private String message;

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (userRepository.findByEmail(email).isEmpty()) {
            return true;
        }
        constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation().disableDefaultConstraintViolation();
        return false;
    }
}

