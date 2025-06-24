package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;

import java.util.List;

public interface FindLocationService {
    List<UserDto> findNearByDonors(double lat, double lng, double radiusKm, String bloodType, Integer userId);
}
