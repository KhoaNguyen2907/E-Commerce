package com.ckt.ecommercecybersoft.user.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class UserExceptionUtils {
    public static final String USER_NOT_FOUND = "005 User not found";
    public static final String USERNAME_ALREADY_EXISTS = "006 Username already exists";
    public static final String EMAIL_ALREADY_EXISTS = "007 Email already exists";
    public static final String USERNAME_LENGTH = "008 Username must be between {min} and {max} characters";
    public static final String PASSWORD_LENGTH = "009 Password must be between {min} and {max} characters";
    public static final String EMAIL_INVALID = "010 Email is not valid";
    public static final String EMAIL_NOT_BLANK = "011 Email is not blank";
    public static final String NAME_NOT_BLANK = "012 Name is not blank";
    public static final String USERNAME_NOT_BLANK = " 013 Name is not blank";
    public static final String PASSWORD_NOT_BLANK = "014 Password is not blank";
    public static final String INCORRECT_PASSWORD = "016 Incorrect password";
}
