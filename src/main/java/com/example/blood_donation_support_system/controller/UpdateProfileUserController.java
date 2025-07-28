package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.response.UpdateResultResponse;
import com.example.blood_donation_support_system.service.user.UpdateProfileUserService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/updateProfile")
public class UpdateProfileUserController {
    @Autowired
    private JwtHelper jwtHelper;
    @Autowired
    private UpdateProfileUserService updateProfileUserService;

    @PostMapping(value = "/update", consumes = {"multipart/form-data"})
    public ResponseEntity<?> updateProfileUser(
            @RequestHeader("Authorization") String auHeader,
            @Valid @ModelAttribute UserRequest userRequest,
            BindingResult bindingResult,
            @RequestPart(required = false) MultipartFile avatarFile) {


        if (bindingResult.hasErrors()) {
            BaseResponse errorResponse = new BaseResponse();
            errorResponse.setCode(400);
            errorResponse.setMessage(bindingResult.getAllErrors().get(0).getDefaultMessage()); // chỉ trả lỗi đầu tiên
            return ResponseEntity.badRequest().body(errorResponse);
        }

        Integer userId = jwtHelper.getUserId(auHeader);
        System.out.println(userId);
        UpdateResultResponse result = updateProfileUserService.updateProfile(userId, userRequest, avatarFile);

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage(result.getMessage());
        baseResponse.setCode(result.isSuccess() ? 200 : 400);
        if(result.isSuccess()){
            return ResponseEntity.ok(baseResponse);
        }else{
            return ResponseEntity.badRequest().body(baseResponse);
        }

    }

//    @PostMapping(value = "/update")
//    public ResponseEntity<?> updateProfileUser(@RequestHeader ("Authorization") String auHeader,
//                                               @RequestParam UserRequest userRequest,
//                                               @RequestParam MultipartFile avatarFile){
//            String userName = jwtHelper.getUsername(auHeader);
//            boolean isSuccess = updateProfileUserService.updateProfile(userName, userRequest,avatarFile);
//            BaseResponse baseResponse = new BaseResponse();
//            if (isSuccess){
//                baseResponse.setMessage("Update profile successfully");
//                baseResponse.setCode(200);
//                return ResponseEntity.ok(baseResponse);
//            }else{
//                baseResponse.setMessage("Update profile failed");
//                baseResponse.setCode(400);
//                return  ResponseEntity.badRequest().body(baseResponse);
//            }
//    }
}
