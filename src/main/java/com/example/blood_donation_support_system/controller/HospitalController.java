package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.service.hospital.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hospitals")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping
    public ResponseEntity<?> getAllHospitals() {
        return ResponseEntity.ok(hospitalService.getAllHospitals());
    }
}
