package com.ckt.ecommercecybersoft.user.model.request;

import javax.validation.constraints.Email;

public class PasswordResetRequestModel {
    @Email(message = "Email is not valid")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
