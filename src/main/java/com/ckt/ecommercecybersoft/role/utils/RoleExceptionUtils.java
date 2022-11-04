package com.ckt.ecommercecybersoft.role.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleExceptionUtils {
    public static final String ROLE_NOT_FOUND = "Role not found";
    public static final String NAME_LENGTH = "Role name must be between {min} and {max} characters";
    public static final String CODE_LENGTH = "Role code must be between {min} and {max} characters";
    public static final String NAME_NOT_BLANK = "Role ame is not blank";
    public static final String CODE_NOT_BLANK = "Role code is not blank";
}
