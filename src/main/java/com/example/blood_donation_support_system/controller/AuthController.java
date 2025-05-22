package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @GetMapping("/social")
    public ResponseEntity<?> getAuthorizationUri(@RequestParam String loginType) {
        BaseResponse response = new BaseResponse();
        String data = authService.generateAuthorizationUri(loginType);
        if(data == null || data.isEmpty()){
            response.setMessage("Failed to generate authorization uri");
            response.setCode(400);
            return ResponseEntity.badRequest().body(response);
        }
        response.setCode(200);
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/social/callback")
    public ResponseEntity<?> authenticateAndFetchProfile(@RequestParam String code, @RequestParam String loginType) {
        BaseResponse response = new BaseResponse();
        Map<String, Object> data = authService.authenticateAndFetchProfile(code, loginType);
        if(data == null || data.isEmpty()){
            response.setMessage("Failed to authenticate and fetch profile");
            response.setCode(400);
            return ResponseEntity.badRequest().body(response);
        }

        // RegisterOrLogin Oauth2 Google
        String token = authService.loginOrSignup(data, "ROLE_MEMBER");
        response.setCode(200);
        response.setData(token);
        return ResponseEntity.ok(response);
    }
}
