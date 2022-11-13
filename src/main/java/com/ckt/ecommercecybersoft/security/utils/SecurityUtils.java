package com.ckt.ecommercecybersoft.security.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@UtilityClass
public class SecurityUtils {
    public static Optional<String> getLoginUsername() {
       return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
               .map(authentication -> {
                   if (authentication.getPrincipal() instanceof String) {
                       return authentication.getPrincipal().toString();
                   }
                   return null;
               });
    }
}
