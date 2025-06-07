package com.example.blood_donation_support_system.service.user;

import java.util.Map;

public interface AuthService {
    String generateAuthorizationUri(String loginType);
    Map<String, Object> authenticateAndFetchProfile(String code, String loginType);
    String loginOrSignup(Map<String, Object> user, String role);
}
