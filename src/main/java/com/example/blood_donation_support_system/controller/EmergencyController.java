package com.example.blood_donation_support_system.controller;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.emergencyservice.EmergencyService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/emergency-requests")
public class EmergencyController {

    @Autowired
    private EmergencyService emergencyService;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping
    public ResponseEntity<?> submitRequest(@RequestBody EmergencyRequest emergencyRequest) {
        emergencyService.handleRequest(emergencyRequest);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Yêu cầu đã được xử lý thành công (có sẵn máu trong kho)");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/no-available-blood")
    public ResponseEntity<?> handleNoAvailableBlood(
            @RequestBody EmergencyRequest emergencyRequest,
            @RequestParam("donorId") Integer donorId,
            @RequestHeader("Authorization") String authId) {


        Integer receiptId = jwtHelper.getUserId(authId);


        emergencyService.handleNoAvailableBlood(emergencyRequest, donorId, receiptId);

        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Đã tạo yêu cầu hiến máu thay thế và đặt lịch hẹn thành công.");
        return ResponseEntity.ok(response);
    }
}
