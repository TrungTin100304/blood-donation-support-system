package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.service.donorandrecipient.FindLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/search")
public class FindLocationController {
    private static final double radiusKm = 10;

    @Autowired
    private FindLocationService findLocationService;

    //hiến máu
    @GetMapping("/donors-nearby")
    public ResponseEntity<?> getNearbyDonors(
            @RequestParam double lat,
            @RequestParam double lng) {
        if(lat == 0 || lng == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bạn chưa cập nhật vị trí. Vui lòng cập nhật để tìm kiếm người hiến máu gần bạn.");
        }
        List<UserDto> result = findLocationService.findNearByDonors(lat, lng, radiusKm);
        return ResponseEntity.ok(result);
    }
}
