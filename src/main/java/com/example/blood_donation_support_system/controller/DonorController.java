package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.EmailService;
import com.example.blood_donation_support_system.service.donorandrecipient.DonorService;
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
    private EmailService emailService;

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


    // Thêm logging để theo dõi hành động
    private static final Logger logger = LoggerFactory.getLogger(DonorController.class);

    // Thêm phương thức xử lý lỗi bổ sung
    private ResponseEntity<BaseResponse> handleError(String message, HttpStatus status) {
        logger.error("Error occurred: {}", message);
        BaseResponse errorResponse = new BaseResponse();
        errorResponse.setCode(status.value());
        errorResponse.setMessage(message);
        return ResponseEntity.status(status).body(errorResponse);
    }

    // Thêm kiểm tra Authorization header trước khi xử lý
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException ex) {
        return handleError(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }



    @PostMapping("/send-email")
    public ResponseEntity<BaseResponse> testEmail(
            @RequestParam String toEmail,
            @RequestHeader("Authorization") String autho,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHienMauGanNhat) {

        try {
            String fullName = jwtHelper.getFullName(autho);
            emailService.sendDonationReminder(fullName, toEmail, ngayHienMauGanNhat);
            BaseResponse response = new BaseResponse();
            response.setCode(HttpStatus.OK.value());
            response.setMessage("Email nhắc hiến máu đã được gửi thành công tới " + toEmail);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Lỗi gửi email: {}", e.getMessage());
            return handleError("Gửi email thất bại: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }






}

