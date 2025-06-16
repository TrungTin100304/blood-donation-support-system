package com.example.blood_donation_support_system.controller;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency-requests")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @PostMapping
    public ResponseEntity<?> submitRequest(@RequestBody EmergencyRequest emergencyRequest) {
        emergencyService.handleRequest(emergencyRequest);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Yêu cầu đã được xử lý thành công (có sẵn máu trong kho)");
        return ResponseEntity.ok(response);
    }
}
