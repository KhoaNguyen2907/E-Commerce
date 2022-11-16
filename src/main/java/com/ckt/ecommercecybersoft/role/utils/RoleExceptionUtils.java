package com.ckt.ecommercecybersoft.role.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleExceptionUtils {
    public static final String ROLE_NOT_FOUND = "017 Role not found";
    public static final String NAME_LENGTH = "018 Role name must be between {min} and {max} characters";
    public static final String CODE_LENGTH = "019 Role code must be between {min} and {max} characters";
    public static final String NAME_NOT_BLANK = "020 Role name is not blank";
    public static final String CODE_NOT_BLANK = "021 Role code is not blank";
}
