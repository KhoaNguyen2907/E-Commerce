package com.ckt.ecommercecybersoft.user.validation.annotation;

import com.ckt.ecommercecybersoft.user.validation.validator.UniqueUsernameValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Username already exists";
    Class[] groups() default {};
    Class[] payload() default {};
}
