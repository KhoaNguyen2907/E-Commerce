package com.ckt.ecommercecybersoft.user.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class MailUtils {
    public static final String EMAIL_VERIFICATION_SUBJECT = "Welcome to Black Adam Shop";
    public static final String EMAIL_VERIFICATION_BODY = "Please click on the link below to verify your email address: ";
    public static final String EMAIL_VERIFICATION_URL = "http://localhost:8080/api/v1/users/email-verifications?token=";

    public static final String PASSWORD_RESET_REQUEST_SUBJECT = "Black Adam-  Password reset request";
    public static final String PASSWORD_RESET_REQUEST_BODY = "Please click on the link below to reset your password: ";

    /**
     * This is URL redirect to FE to verify token, if true, FE will redirect to reset password api
     */
    public static final String PASSWORD_RESET_URL = "http://localhost:8080/api/v1/users/verify-password-reset-token?token=";


    public static final String PASSWORD_RESET_SUBJECT = "Black Adam -  Password reset successful";
    public static final String PASSWORD_RESET_BODY = "Your password has been reset successfully";

    public static final String EMAIL_NAME = "BlackAdam";

}
