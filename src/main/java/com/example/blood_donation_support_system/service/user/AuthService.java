package com.example.blood_donation_support_system.service.user;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface AuthService {
    String generateAuthorizationUri(String loginType);
    Map<String, Object> authenticateAndFetchProfile(String code, String loginType);
    String loginOrSignup(Map<String, Object> user, String role);
    void sentOtpToEmail(String email) throws MessagingException;
    void resetPassword(String email, String otp,String newPassword);
}
