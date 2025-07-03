package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.UserDto;

import java.util.List;

public interface FindCompatibleDonorsByComponentService {
    List<String> findCompatibleTypesByComponent(String recipentBloodType, String component);
}
