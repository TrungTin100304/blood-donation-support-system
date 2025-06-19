package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.exception.LocationException;
import com.example.blood_donation_support_system.repository.BloodUnitRepository;
import com.example.blood_donation_support_system.repository.HospitalRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.RegisterDonorRequest;

import com.example.blood_donation_support_system.utils.JwtHelper;
import jakarta.mail.internet.MimeMessage;

import com.example.blood_donation_support_system.service.BloodInventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



import java.time.LocalDate;

import java.time.format.DateTimeFormatter;

import java.util.List;


@Service
public class DonorServiceImp implements DonorService {
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private DonationReminderService donationReminderService;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private BloodInventoryService bloodInventoryService;

    @Autowired
    private BloodUnitRepository bloodUnitRepository;

    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public void registerDonor(int userId, RegisterDonorRequest registerDonorRequest) {
        UserEntity userEntity = userRepository.findById(userId).
                orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        if (userEntity.getBloodType() != null && userEntity.getReadyTime() != null) {
            throw new IllegalArgumentException("User already has ready time");
        }
        List<String> valiBloodTypes = List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if (!valiBloodTypes.contains(registerDonorRequest.getBloodType())) {
            throw new IllegalArgumentException("Blood type is not valid");
        }

        // ✅ Cập nhật latitude và longitude nếu có
        if (registerDonorRequest.getLatitude() != 0 && registerDonorRequest.getLongitude() != 0) {
            userEntity.setLatitude(registerDonorRequest.getLatitude());
            userEntity.setLongitude(registerDonorRequest.getLongitude());
        } else {
            throw new LocationException("Latitude and Longitude must not be null");
        }


        userEntity.setBloodType(registerDonorRequest.getBloodType());
        userEntity.setReadyTime(registerDonorRequest.getReadyTime());
        userEntity.setNote(registerDonorRequest.getNote());


        userRepository.save(userEntity);


    }

//    @Override
//    public void sendDonationReminderEmail(String authHeader, String toEmail, LocalDate lastDonationDate) {
//        String fullName = jwtHelper.getFullName(authHeader);
//        LocalDate nextDonationDate = lastDonationDate.plusMonths(2);
//
//        String subject = "Email từ Hệ thống Hiến máu";
//        String body = String.format("""
//                        Kính gửi %s,
//
//                        Cảm ơn bạn đã tham gia hiến máu và đóng góp quý báu cho cộng đồng.
//
//                        Chúng tôi xin thông báo rằng thời gian phục hồi tối thiểu sau mỗi lần hiến máu toàn phần là 2 tháng (60 ngày).
//                        Trong khoảng thời gian này, cơ thể bạn cần nghỉ ngơi và phục hồi để đảm bảo sức khỏe cho những lần hiến máu tiếp theo.
//
//                        👉 Ngày hiến máu gần nhất của bạn: %s
//                        👉 Ngày có thể hiến máu tiếp theo: %s
//
//                        Chúng tôi sẽ gửi lời nhắc khi bạn đã sẵn sàng cho lần hiến máu tiếp theo.
//
//                        Một lần nữa, xin chân thành cảm ơn nghĩa cử cao đẹp của bạn.
//                        Mọi giọt máu bạn cho đi đều có thể cứu sống một sinh mạng.
//
//                        Trân trọng,
//                        Trung tâm Hiến máu Quốc gia
//                        bloodsystem123@gmail.com – 1900 123 456
//                        """,
//                fullName,
//                lastDonationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
//                nextDonationDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
//        );
//
//        try {
//            MimeMessage message = mailSender.createMimeMessage();
//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setTo(toEmail);
//            helper.setFrom("bloodsystem123@gmail.com");
//            helper.setSubject(subject);
//            helper.setText(body.replace("\n", "<br>"), true); // HTML format
//            mailSender.send(message);
//        } catch (Exception e) {
//            throw new RuntimeException("Không thể gửi email: " + e.getMessage());
//        }
//    }
}

