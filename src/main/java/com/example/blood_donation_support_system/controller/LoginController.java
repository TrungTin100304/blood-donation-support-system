package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.user.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin(origins = "*")
public class LoginController {
    @Autowired
    private LoginServiceImp loginServiceImp;
    @PostMapping
    public ResponseEntity <?> login(@RequestBody UserEntity userEntity){
        String token = loginServiceImp.login(userEntity.getUserName(), userEntity.getPassword());
        BaseResponse response = new BaseResponse();
        if(token == null || token.isEmpty()){
            response.setMessage("Login failed");
            response.setCode(400);
            return ResponseEntity.badRequest().body(response);
        }
        response.setData(token);
        response.setCode(200);

        return ResponseEntity.ok(response);
    }
}
