package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.BloodUnitDto;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.repository.BloodUnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BloodUnitServiceImp implements BloodUnitService {

    @Autowired
    private BloodUnitRepository bloodUnitRepository;

    @Override
    public List<BloodUnitDto> getAllBloodUnits() {
            List<BloodUnitEntity> entities = bloodUnitRepository.findAll();
            return entities.stream().map(this::convertToDto).collect(Collectors.toList());
        }

        private BloodUnitDto convertToDto (BloodUnitEntity entity){
            BloodUnitDto dto = new BloodUnitDto();
            dto.setBloodUnitId(entity.getBloodUnitId());
            dto.setBloodType(entity.getBloodType());
            dto.setComponentType(entity.getComponentType());
            dto.setQuantity(entity.getQuantity());
            dto.setReceivedDate(entity.getReceivedDate());
            dto.setExpiryDate(entity.getExpiryDate());
            if (entity.getUser() != null) {
                dto.setUserId(entity.getUser().getUserId());
                dto.setUserName(entity.getUser().getUserName());
            }
            if (entity.getBloodInventory() != null) {
                dto.setInventoryId(entity.getBloodInventory().getInventoryId());
                dto.setInventoryStatus(entity.getBloodInventory().getStatus());
                if (entity.getBloodInventory().getHospital() != null) {
                    dto.setHospitalId(entity.getBloodInventory().getHospital().getHospitalId());
                }
            }
            return dto;
        }
    }

