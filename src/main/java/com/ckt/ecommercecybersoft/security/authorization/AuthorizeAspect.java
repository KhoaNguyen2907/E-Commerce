package com.ckt.ecommercecybersoft.security.authorization;

import com.ckt.ecommercecybersoft.common.exception.ForbiddenException;
import com.ckt.ecommercecybersoft.common.exception.NotFoundException;
import com.ckt.ecommercecybersoft.user.model.User;
import com.ckt.ecommercecybersoft.user.repository.UserRepository;
import com.ckt.ecommercecybersoft.user.utils.UserExceptionUtils;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AuthorizeAspect {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizeAspect.class);
    @Autowired
    private UserRepository userRepository;

//    @Pointcut("execution(* com.ckt.ecommercecybersoft.user.controller.UserController.getAllUsers(..))")
//    public void getAllUsers() {
//    }
//
//    @Pointcut("execution(public * com.ckt.ecommercecybersoft.user.controller.UserController.updateUser(..))")
//    public void updateUser() {
//    }
//
//    @Pointcut("execution(public * com.ckt.ecommercecybersoft.user.controller.UserController.deleteUser(..))")
//    public void deleteUser() {
//    }
//
//    @Pointcut("execution( public * com.ckt.ecommercecybersoft.user.controller.UserController.changeRole(..))")
//    public void changeRole() {
//    }
//
//    @Pointcut("getAllUsers() ||  updateUser() || deleteUser() || changeRole()")
//    public void adminOnly() {
//    }
//
//    @Before("adminOnly()")
//    public void checkAdminzz() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String username = authentication.getName();
//        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
//        if (!user.getRole().equals("ADMIN")) {
//            throw new ForbiddenException("You are not authorized to perform this action");
//        }
//    }

    @Before("@annotation(com.ckt.ecommercecybersoft.security.authorization.AdminOnly)")
    public void checkAdmin() {
        Authentication authentication = this.getAuthentication();
        String username = authentication.getName();
        logger.info("Current user: " + username);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException(UserExceptionUtils.USER_NOT_FOUND));
        if (!user.getRole().getCode().equals("ADMIN")) {
            logger.error("Can not access this resource because you are not admin");
            throw new ForbiddenException("Don't have permission");
        }
    }

    private Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}

