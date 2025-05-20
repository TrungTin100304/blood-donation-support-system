package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.RegisterServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    private static final String ADMIN_ROLE = "ROLE_ADMIN";
    private static final String STAFF_ROLE = "ROLE_STAFF";
    private static final String MEMBER_ROLE = "ROLE_MEMBER";


    @Autowired
    private RegisterServiceImp registerServiceImp;


    @PostMapping()
    public ResponseEntity<?> registerAccount(@RequestBody UserRequest request) {

        registerServiceImp.register(request, MEMBER_ROLE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.getTenDangNhap()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/admin")
    public ResponseEntity<?> registerAdmin(@RequestBody UserRequest request) {

        registerServiceImp.register(request, ADMIN_ROLE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.getTenDangNhap()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }

    @PostMapping("/staff")
    public ResponseEntity<?> registerStaff(@RequestBody UserRequest request) {

        registerServiceImp.register(request, STAFF_ROLE);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(200);
        baseResponse.setMessage("Register " + request.getTenDangNhap()  + " successfully");
        baseResponse.setData(200);
        return ResponseEntity.ok(baseResponse);
    }
}
