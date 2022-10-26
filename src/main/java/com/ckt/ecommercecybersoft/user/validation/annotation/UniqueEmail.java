package com.ckt.ecommercecybersoft.user.validation.annotation;

import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import com.ckt.ecommercecybersoft.user.validation.validator.UniqueEmailValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default UserExceptionUtils.EMAIL_ALREADY_EXISTS;
    Class[] groups() default {};
    Class[] payload() default {};
}
