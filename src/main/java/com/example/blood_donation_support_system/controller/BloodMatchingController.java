package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.request.BloodUnitRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.BloodMatchingService;
import com.example.blood_donation_support_system.service.FindCompatibleDonorsByComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/matching")
public class BloodMatchingController {
    @Autowired
    private BloodMatchingService bloodMatchingService;

    @Autowired
    private FindCompatibleDonorsByComponentService findCompatibleDonorsByComponentService;

    @GetMapping("/blood-types/component")
    public ResponseEntity<?> findCompatibleDonorsByComponent(@RequestBody BloodUnitRequest bloodUnitRequest) {
        List<String> data = findCompatibleDonorsByComponentService.findCompatibleTypesByComponent(bloodUnitRequest.getBloodType(), bloodUnitRequest.getComponentType());
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Danh sách nhóm máu có thể hiến cho người nhận " + bloodUnitRequest.getBloodType()+ " theo thành phần " +  bloodUnitRequest.getComponentType());
        response.setData(data);
        return ResponseEntity.ok(response);
    }
}
