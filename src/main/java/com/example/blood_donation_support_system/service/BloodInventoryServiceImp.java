package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.BloodInventoryDto;
import com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO;
import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.HospitalEntity;
import com.example.blood_donation_support_system.repository.BloodInventoryRepository;
import com.example.blood_donation_support_system.repository.BloodUnitRepository;
import com.example.blood_donation_support_system.repository.HospitalRepository;
import com.example.blood_donation_support_system.request.BloodInventoryRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

import java.util.List;

import java.util.stream.Collectors;

@Service
public class BloodInventoryServiceImp implements BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;
    @Autowired
    private HospitalRepository hospitalRepository;
    @Autowired
    private BloodUnitRepository bloodUnitRepository; // Thêm repository

    @Override
    public BloodInventoryDto updateBloodInventory(BloodInventoryRequest request) {
        // Lấy HospitalEntity
        HospitalEntity hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with ID: " + request.getHospitalId()));

        // Lấy BloodUnitEntity
        BloodUnitEntity bloodUnit = bloodUnitRepository.findById(request.getBloodUnitId())
                .orElseThrow(() -> new EntityNotFoundException("Blood unit not found with ID: " + request.getBloodUnitId()));

        // Tìm hoặc tạo mới BloodInventoryEntity
        BloodInventoryEntity entity = bloodInventoryRepository.findByBloodUnitBloodUnitId(request.getBloodUnitId())
                .orElse(new BloodInventoryEntity());

        entity.setHospital(hospital);
        entity.setBloodUnit(bloodUnit); // Gán BloodUnitEntity
        entity.setStatus(request.getStatus() != null ? request.getStatus() : BloodInventoryEntity.BloodInventoryStatus.IN_STOCK);
        entity.setLastUpdate(LocalDateTime.now());

        // Cập nhật BloodUnitEntity
        if (request.getQuantity() > 0) {
            bloodUnit.setQuantity(request.getQuantity());
        }
        if (request.getReceivedDate() != null) {
            bloodUnit.setReceivedDate(request.getReceivedDate());
        }
        if (request.getExpiryDate() != null) {
            bloodUnit.setExpiryDate(request.getExpiryDate());
        }
        // Lưu BloodUnitEntity
        bloodUnitRepository.save(bloodUnit);

        entity = bloodInventoryRepository.save(entity);

        return convertToDto(entity);
    }

    @Override
    public List<BloodInventoryDto> getInventoryByHospital(Integer hospitalId) {
        List<BloodInventoryEntity> entities = bloodInventoryRepository.findByHospitalHospitalId(hospitalId);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BloodInventoryDto> getInventoryByHospitalName(String hospitalName) {
//        HospitalEntity hospital = hospitalRepository.findByHospitalName(hospitalName)
//                .orElseThrow(() -> new EntityNotFoundException("Hospital not found"));
        List<BloodInventoryEntity> entities = bloodInventoryRepository.findByHospitalNameContainingIgnoreCase(hospitalName);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BloodInventoryDto> getInventoryByBloodType(String bloodType) {
        List<BloodInventoryEntity> entity = bloodInventoryRepository.findByBloodUnitBloodType(bloodType);
//                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for blood unit type: " + bloodType));
        return entity.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BloodInventoryDto> getAllInventory() {
        List<BloodInventoryEntity> entities = bloodInventoryRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    @Override
    public List<BloodQuantityByTypeDTO> getBloodQuantityByType(@RequestParam("hospitalId") Integer hospitalId ) {
        List<BloodQuantityByTypeDTO> total = bloodInventoryRepository.findBloodQuantityByType(hospitalId);
        return total.stream()
                .map(proj -> new BloodQuantityByTypeDTO(
                        proj.getName(),
                        proj.getBloodType(),
                        proj.getComponentType(),
                        proj.getTotalQuantity() != null ? proj.getTotalQuantity() : 0


                ))
                .collect(Collectors.toList());
    }


    private BloodInventoryDto convertToDto(BloodInventoryEntity entity) {
        BloodInventoryDto dto = new BloodInventoryDto();
        dto.setInventoryId(entity.getInventoryId());
        dto.setBloodUnitId(entity.getBloodUnit().getBloodUnitId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setStatus(entity.getStatus());
        if(entity.getBloodUnit() != null) {
            BloodUnitEntity bloodUnit = entity.getBloodUnit();
            dto.setBloodType(bloodUnit.getBloodType());
            dto.setComponentType(bloodUnit.getComponentType());
            dto.setQuantity(bloodUnit.getQuantity());
            dto.setReceivedDate(bloodUnit.getReceivedDate());
            dto.setStatusUnit(bloodUnit.getStatus());
            dto.setExpiryDate(bloodUnit.getReceviedDate().plusDays(28));
        }
        if(entity.getHospital() != null) {
            dto.setHospitalId(entity.getHospital().getHospitalId());
            dto.setName(entity.getHospital().getName());
        }
        return dto;


    }


}