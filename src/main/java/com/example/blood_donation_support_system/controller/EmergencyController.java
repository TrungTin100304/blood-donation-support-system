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
         EmergencyEntity issucced =  emergencyServiceImp.registerEmergency(request, userIdInt);
         BaseResponse response = new BaseResponse();
        if (issucced != null) {
            response.setCode(200);
            response.setMessage("Đăng ký Emergency Blood thành công");
            response.setData(issucced.getRequestId());
            return ResponseEntity.ok(response);
        } else {
            response.setCode(500);
            response.setMessage("Đăng ký Emergency Blood thất bại");
            return ResponseEntity.status(500).body(response);
        }
    }
}
