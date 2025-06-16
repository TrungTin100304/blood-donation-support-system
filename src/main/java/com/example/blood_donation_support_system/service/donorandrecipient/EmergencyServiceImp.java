//package com.example.blood_donation_support_system.service.donorandrecipient;
//
//import com.example.blood_donation_support_system.entity.EmergencyEntity;
//import com.example.blood_donation_support_system.exception.HospitalNotFoundException;
//import com.example.blood_donation_support_system.exception.UserNotFoundException;
//import com.example.blood_donation_support_system.repository.EmergencyRepository;
//import com.example.blood_donation_support_system.repository.HospitalRepository;
//import com.example.blood_donation_support_system.repository.UserRepository;
//import com.example.blood_donation_support_system.request.EmergencyRequest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//
//@Service
//public class EmergencyServiceImp implements EmergencyService{
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private EmergencyRepository emergencyRepository;
//
//    @Autowired
//    private HospitalRepository hospitalRepository;
//    @Override
//    public EmergencyEntity registerEmergency(EmergencyRequest emergencyRequest, int userId) {
//        EmergencyEntity emergencyEntity = new EmergencyEntity();
//
//        emergencyEntity.setRequester(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId)));
//
//        emergencyEntity.setHospital(hospitalRepository.findByName(emergencyRequest.getHospitalName())
//                .orElseThrow(() -> new HospitalNotFoundException("Hospital not found with name: " + emergencyRequest.getHospitalName())));
//
//        emergencyEntity.setBloodType(emergencyRequest.getBloodType());
//        emergencyEntity.setNote(emergencyRequest.getNote());
//        emergencyEntity.setQuantity(emergencyRequest.getQuantity());
//        emergencyEntity.setCreatedAt(LocalDateTime.now());
//        emergencyEntity.setStatus("Pending");
//        emergencyEntity.setNeededTime(emergencyRequest.getNeededTime());
//        emergencyEntity.setComponentType(emergencyRequest.getComponentType());
//
//        return emergencyRepository.save(emergencyEntity);
//    }
//}
