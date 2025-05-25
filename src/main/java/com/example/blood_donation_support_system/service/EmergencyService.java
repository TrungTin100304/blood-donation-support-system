package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.EmergencyEntity;
import com.example.blood_donation_support_system.request.EmergencyRequest;

public interface EmergencyService {
    EmergencyEntity registerEmergency(EmergencyRequest emergencyRequest, int userId);
}
