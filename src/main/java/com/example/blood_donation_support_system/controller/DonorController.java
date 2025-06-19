package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.DonorService;
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
    private JavaMailSender mailSender;

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



//    @PostMapping("/send-email")
//    public ResponseEntity<BaseResponse> testEmail(
//            @RequestParam String toEmail,
//            @RequestHeader("Authorization") String autho,
//            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHienMauGanNhat) {
//
//            String fullName = jwtHelper.getFullName(autho);
//
//        try {
//            // Tính ngày có thể hiến máu tiếp theo: ngày hiến máu + 2 tháng
//            LocalDate ngayTiepTheo = ngayHienMauGanNhat.plusMonths(2);
//
//            String subject = "Email từ Hệ thống Hiến máu";
//            String body = String.format("""
//            Kính gửi %s,
//
//            Cảm ơn bạn đã tham gia hiến máu và đóng góp quý báu cho cộng đồng.
//
//            Chúng tôi xin thông báo rằng thời gian phục hồi tối thiểu sau mỗi lần hiến máu toàn phần là 2 tháng (60 ngày).
//            Trong khoảng thời gian này, cơ thể bạn cần nghỉ ngơi và phục hồi để đảm bảo sức khỏe cho những lần hiến máu tiếp theo.
//
//            👉 Ngày hiến máu gần nhất của bạn: %s
//            👉 Ngày có thể hiến máu tiếp theo: %s
//
//            Chúng tôi sẽ gửi lời nhắc khi bạn đã sẵn sàng cho lần hiến máu tiếp theo.
//
//            Một lần nữa, xin chân thành cảm ơn nghĩa cử cao đẹp của bạn.
//            Mọi giọt máu bạn cho đi đều có thể cứu sống một sinh mạng.
//
//            Trân trọng,
//            Trung tâm Hiến máu Quốc gia
//            bloodsystem123@gmail.com – 1900 123 456
//            """,
//                    fullName,
//                    ngayHienMauGanNhat.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                    ngayTiepTheo.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//            );
//
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setTo(toEmail);
//            helper.setFrom("bloodsystem123@gmail.com");
//            helper.setSubject(subject);
//            helper.setText(body.replace("\n", "<br>"), true); // HTML format
//
//            mailSender.send(message);
//
//            logger.info("Test email sent successfully to {}", toEmail);
//
//            BaseResponse response = new BaseResponse();
//            response.setCode(HttpStatus.OK.value());
//            response.setMessage("Test email sent successfully to " + toEmail);
//            return ResponseEntity.ok(response);
//
//        } catch (Exception e) {
//            logger.error("Failed to send test email to {}: {}", toEmail, e.getMessage());
//            return handleError("Failed to send test email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }


    @PostMapping("/send-email")
    public ResponseEntity<BaseResponse> testEmail(
            @RequestParam String toEmail,
            @RequestHeader("Authorization") String autho,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate ngayHienMauGanNhat) {

        try {
            donorService.sendDonationReminderEmail(autho, toEmail, ngayHienMauGanNhat);
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

