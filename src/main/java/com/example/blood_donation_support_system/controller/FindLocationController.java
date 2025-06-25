package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.service.donorandrecipient.FindLocationService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import io.jsonwebtoken.JwtHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/search")
public class FindLocationController {
    private static final double radiusKm = 2;

    @Autowired
    private FindLocationService findLocationService;

    @Autowired
    private JwtHelper  jwtHelper;
    //hiến máu

    @PostMapping ("/donors-nearby")
    public ResponseEntity<?> getNearbyDonors(
            @RequestBody UserRequest userRequest,
            @RequestHeader("Authorization") String author) {
        if(userRequest.getLatitude() == 0 || userRequest.getLongitude() == 0){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Bạn chưa cập nhật vị trí. Vui lòng cập nhật để tìm kiếm người hiến máu gần bạn.");
        }
        Integer userId = jwtHelper.getUserId(author);
        List<UserDto> result = findLocationService.findNearByDonors(userRequest.getLatitude(), userRequest.getLongitude(), radiusKm, userId);
        return ResponseEntity.ok(result);
    }
}
