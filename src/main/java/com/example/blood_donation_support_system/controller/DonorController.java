package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.donorandrecipient.DonorService;
import com.example.blood_donation_support_system.service.user.GetUserService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
