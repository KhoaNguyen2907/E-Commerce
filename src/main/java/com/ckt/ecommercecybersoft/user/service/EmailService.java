package com.ckt.ecommercecybersoft.user.service;

/**
 * Email service
 * @author KhoaNguyen
 */
public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
