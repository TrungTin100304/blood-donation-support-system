package com.example.blood_donation_support_system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;


@Service
public class AuthServiceImp implements AuthService {



    // Oauth2 Google
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String googleRedirectUri;
    @Value("${spring.security.oauth2.client.registration.google.auth-uri}")
    private String googleAuthUri;
    @Value("${spring.security.oauth2.client.registration.google.token-uri}")
    private String googleTokenUri;
    @Value("${spring.security.oauth2.client.registration.google.user-info-uri}")
    private String googleUserInfoUri;


    @Override
    public String generateAuthorizationUri(String loginType) {
        String url = "";
        loginType = loginType.toLowerCase();
        switch (loginType) {
            case "google":
                url = googleAuthUri + "?client_id=" + googleClientId + "&redirect_uri=" + googleRedirectUri + "&scope=email%20profile" + "&response_type=code" + "&loginType=" + loginType;
                break;
            default:
        }
        return url;
    }

        @Override
        public Map<String, Object> authenticateAndFetchProfile(String code, String loginType) {
            RestTemplate restTemplate = new RestTemplate();
            loginType = loginType.toLowerCase();
            String accessToken = "";
            String url = "";
            String userInfoUri = "";
            Map<String, Object>  userInfo = null;
            switch (loginType) {
                case "google":
                    // Call Google API to get user profile
                    Map<String, String> request = Map.of(
                            "client_id", googleClientId, "redirect_uri", googleRedirectUri,
                            "client_secret", googleClientSecret, "code", code,
                            "grant_type", "authorization_code"
                    );
                    ResponseEntity res = restTemplate.postForEntity(googleTokenUri, request, Map.class);

                    String body = res.getBody().toString();
                    accessToken = body.split(",")[0].replace("{access_token=", "");
                    userInfoUri = googleUserInfoUri + "?access_token=" + accessToken;
                    break;
                default:
            }

            userInfo = restTemplate.getForObject(userInfoUri, Map.class);

            return userInfo;
        }

    @Override
    public String loginOrSignup(Map<String, Object> userInfo, String role) {


        return "";
    }
}
