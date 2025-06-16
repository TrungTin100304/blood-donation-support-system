package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.*;
import com.example.blood_donation_support_system.exception.BloodUnitNotFoundException;
import com.example.blood_donation_support_system.repository.BloodInventoryRepository;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import com.example.blood_donation_support_system.repository.EmergencyRepository;
import com.example.blood_donation_support_system.repository.HospitalRepository;
import com.example.blood_donation_support_system.request.EmergencyRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class EmergencyServiceImp implements EmergencyService {
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;
    @Override
    @Transactional
    public void handleRequest(EmergencyRequest request) {
        //B1: Lưu yêu cầu vào emergency_request
        HospitalEntity hospitalId = hospitalRepository.findById(request.getHospitalId()).orElse(null);
        EmergencyEntity emergencyEntity = new EmergencyEntity();
        emergencyEntity.setBloodType(request.getBloodType());
        emergencyEntity.setComponentType(request.getComponentType());
        emergencyEntity.setHospital(hospitalId);
        emergencyEntity.setQuantity(request.getQuantity());
        emergencyEntity.setNeededTime(request.getNeededTime());
        emergencyEntity.setStatus("Pending");
        emergencyRepository.save(emergencyEntity);
        //B2: Tìm máu phù hợp trong kho
        Optional<BloodInventoryEntity> optionalBloodInventory = bloodInventoryRepository.findFirstByStatusAndHospital_HospitalIdAndBloodUnit_BloodTypeAndBloodUnit_ComponentType(BloodInventoryEntity.BloodInventoryStatus.In_Stock, request.getHospitalId(), request.getBloodType(), request.getComponentType());
        if(optionalBloodInventory.isPresent()){
            BloodInventoryEntity inventory = optionalBloodInventory.get();
            //B3 cập nhật BloodInventoryStatus = Used
            inventory.setStatus(BloodInventoryEntity.BloodInventoryStatus.Used);

            //B4 cập nhật BloodUnit.status = 'Used'
            BloodUnitEntity bloodUnit =  inventory.getBloodUnit();
            bloodUnit.setStatus("Used");
            bloodInventoryRepository.save(inventory);

            //B5 ghi log vào donation_history (user_id = null)
            DonationHistoryEntity donationHistoryEntity = new DonationHistoryEntity();
            donationHistoryEntity.setUser(null);
            donationHistoryEntity.setBloodUnitId(bloodUnit);
            donationHistoryEntity.setDonationDate(LocalDateTime.now());
            donationHistoryEntity.setRecoveryTime(null);
            donationHistoryEntity.setRecoveryStatus("N/A");
            donationHistoryRepository.save(donationHistoryEntity);

            //B6: Cập nhật emergency_request.data = "Complete"
            emergencyEntity.setStatus("Completed");
            emergencyRepository.save(emergencyEntity);
        }else{
            // Không có máu => không xử lý ở đây (trường hợp này xử lý ở flow khác)
            throw new BloodUnitNotFoundException("Không có đơn vị máu phù hợp trong kho.");
        }

    }
}
