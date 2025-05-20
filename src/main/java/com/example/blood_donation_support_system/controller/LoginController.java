package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.LoginServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@CrossOrigin()
public class LoginController {
    @Autowired
    private LoginServiceImp loginServiceImp;
    @PostMapping
    public ResponseEntity <?> login(@RequestBody UserEntity nguoiDungEntity){
        String token = loginServiceImp.login(nguoiDungEntity.getTenDangNhap(), nguoiDungEntity.getMatKhau());
        System.out.println(token);
        BaseResponse response = new BaseResponse();
        response.setData(token);
        response.setCode(200);

        return ResponseEntity.ok(response);
    }
}
