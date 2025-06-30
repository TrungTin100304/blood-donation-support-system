package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.BloodUnitDto;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.BloodUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blood-units")
public class BloodUnitController {
    @Autowired
    private BloodUnitService bloodUnitService;

    @GetMapping

    public ResponseEntity<List<BloodUnitDto>> getAllBloodUnits() {
        List<BloodUnitDto> bloodUnits = bloodUnitService.getAllBloodUnits();
        return ResponseEntity.ok(bloodUnits);
    }
}