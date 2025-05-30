package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.UpdateProfileUserService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/updateProfile")
public class UpdateProfileUserController {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UpdateProfileUserService updateProfileUserService;
    @PostMapping(value = "/update")
    public ResponseEntity<?> updateProfileUser(@RequestHeader ("Authorization") String auHeader,
                                               @RequestPart UserRequest userRequest,
                                               @RequestPart MultipartFile avatarFile){
            String userName = jwtHelper.getUsername(auHeader);
            boolean isSuccess = updateProfileUserService.updateProfile(userName, userRequest,avatarFile);
            BaseResponse baseResponse = new BaseResponse();
            if (isSuccess){
                baseResponse.setMessage("Update profile successfully");
                baseResponse.setCode(200);
                return ResponseEntity.ok(baseResponse);
            }else{
                baseResponse.setMessage("Update profile failed");
                baseResponse.setCode(400);
                return  ResponseEntity.badRequest().body(baseResponse);
            }
    }
}
