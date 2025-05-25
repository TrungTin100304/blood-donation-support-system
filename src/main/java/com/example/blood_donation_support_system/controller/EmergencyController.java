package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.entity.EmergencyEntity;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.EmergencyServiceImp;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency")
public class EmergencyController {
    @Autowired
    private EmergencyServiceImp emergencyServiceImp;

    @Autowired
    private JwtHelper jwtHelper;
    @PostMapping("/register")
    public ResponseEntity<?> registerEmergency(@RequestBody EmergencyRequest request, @RequestHeader("Authorization") String userId){
         Integer userIdInt = jwtHelper.getUserId(userId);
         EmergencyEntity saved =  emergencyServiceImp.registerEmergency(request, userIdInt);
         BaseResponse response = new BaseResponse();
         response.setCode(200);
         response.setMessage("Đăng ký Emergency Blood thành công");
         return ResponseEntity.ok(response);


    }
}
