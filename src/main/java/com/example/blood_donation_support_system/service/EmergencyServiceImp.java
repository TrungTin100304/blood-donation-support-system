package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.EmergencyEntity;
import com.example.blood_donation_support_system.repository.EmergencyRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EmergencyServiceImp implements EmergencyService{

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Override
    public EmergencyEntity registerEmergency(EmergencyRequest emergencyRequest, int userId) {
        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.setRequesterId(userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)));
        emergencyEntity.setBloodType(emergencyRequest.getBloodType());
        emergencyEntity.setNote(emergencyRequest.getNote());
        emergencyEntity.setQuantity(emergencyRequest.getQuantity());
        emergencyEntity.setCreatedAt(LocalDateTime.now());
        emergencyEntity.setStatus("Pending");
        emergencyEntity.setNeededTime(emergencyRequest.getNeededTime());
        emergencyEntity.setComponentType(emergencyRequest.getComponentType());
        emergencyEntity.setHospitalName(emergencyRequest.getHospitalName());
        return emergencyRepository.save(emergencyEntity);
    }
}
