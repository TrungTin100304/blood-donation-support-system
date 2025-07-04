package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.donorandrecipient.DonorService;
import com.example.blood_donation_support_system.service.donorandrecipient.GetUserService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/api/donor")
public class DonorController {
    @Autowired
    private DonorService donorService;
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private GetUserService getUserService;


    @PostMapping("/register")
    public ResponseEntity<?> registerDonor(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody RegisterDonorRequest registerDonorRequest){


        Integer userId = jwtHelper.getUserId(authHeader);
        donorService.registerDonor(userId, registerDonorRequest);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setMessage("Cập nhật nhóm máu & thời điểm sẵn sàng thành công.");
        baseResponse.setData(userId);
        baseResponse.setCode(200);
        return ResponseEntity.ok(baseResponse);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllDonors(){
        return ResponseEntity.ok(getUserService.getAllDonors());
    }

}
