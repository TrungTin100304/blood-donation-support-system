package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.BloodUnitRepository;
import com.example.blood_donation_support_system.repository.HospitalRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.BloodInventoryRequest;
import com.example.blood_donation_support_system.request.DonationHistoryRequest;
import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.List;


@Service
public class DonorServiceImp implements DonorService{
    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private DonationReminderService donationReminderService;

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

        if(userEntity.getBloodType() != null && userEntity.getReadyTime() != null){
            throw new IllegalArgumentException("User already has ready time");
        }
        List<String> valiBloodTypes = List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-");
        if(!valiBloodTypes.contains(registerDonorRequest.getBloodType())){
            throw new IllegalArgumentException("Blood type is not valid");
        }

        userEntity.setBloodType(registerDonorRequest.getBloodType());
        userEntity.setReadyTime(registerDonorRequest.getReadyTime());
        userRepository.save(userEntity);


    }
}
