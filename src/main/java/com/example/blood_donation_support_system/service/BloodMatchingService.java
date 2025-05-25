package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.UserEntity;

import java.util.List;

public interface BloodMatchingService {
    List<UserEntity> findCompatibleDonor(String recipentBloodType);

}
