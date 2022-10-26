package com.ckt.ecommercecybersoft.user.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UrlUtils {
    public static final String USER_API_V1 = "/api/v1/users";
    public static final String BY_ID = "/{id}";
    public static final String SIGN_UP = "/sign-up";
    public static final String EMAIL_VERIFICATION = "/email-verifications";
    public static final String PASSWORD_RESET_REQUEST = "/password-reset-request";
    public static final String RESET_PASSWORD = "/password-reset";
    public static final String VERIFY_PASSWORD_RESET_TOKEN = "/verify-password-reset-token";
}
