package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.BloodMatchingService;
import com.example.blood_donation_support_system.service.FindCompatibleDonorsByComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
public class BloodMatchigController {
    @Autowired
    private BloodMatchingService bloodMatchingService;

    @Autowired
    private FindCompatibleDonorsByComponentService findCompatibleDonorsByComponentService;
    @GetMapping("/donnors")
    public ResponseEntity<?> findCompatibleDonors(@RequestParam String recipientBloodType) {
        List<UserDto> donors = bloodMatchingService.findCompatibleDonor(recipientBloodType);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách người hiến phù hợp");
        response.setData(donors);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/donnors/component")
    public ResponseEntity<?> findCompatibleDonorsByComponent(@RequestParam String recipientBloodType, @RequestParam String component) {
        List<UserDto> donors = findCompatibleDonorsByComponentService.findCompatibleTypesByComponent(recipientBloodType, component);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách người hiến phù hợp");
        response.setData(donors);
        return ResponseEntity.ok(response);
    }
}
