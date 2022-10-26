package com.ckt.ecommercecybersoft.user.service;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
