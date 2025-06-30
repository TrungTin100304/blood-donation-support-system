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



import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.List;


@Service
public class DonorServiceImp implements DonorService {
    @Autowired
    private UserRepository userRepository;


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

        // Nếu đã có readyTime, thì kiểm tra thời gian phục hồi (ví dụ: 2 tháng)
        if (userEntity.getReadyTime() != null) {
            LocalDateTime lastReadyTime = userEntity.getReadyTime();
            LocalDateTime now = LocalDateTime.now();

            long monthsSinceLastDonation = ChronoUnit.MONTHS.between(lastReadyTime, now);
            if (monthsSinceLastDonation < 2) {
                throw new IllegalArgumentException("You must wait 2 months before donating again");
            }
        }


        userEntity.setBloodType(registerDonorRequest.getBloodType());
        userEntity.setReadyTime(registerDonorRequest.getReadyTime());
        userEntity.setNote(registerDonorRequest.getNote());


        userRepository.save(userEntity);


    }


}

