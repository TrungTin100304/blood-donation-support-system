//package com.example.blood_donation_support_system.service;
//
//import com.example.blood_donation_support_system.dto.BloodInventoryDto;
//import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
//import com.example.blood_donation_support_system.entity.HospitalEntity;
//import com.example.blood_donation_support_system.repository.BloodInventoryRepository;
//import com.example.blood_donation_support_system.repository.HospitalRepository;
//import com.example.blood_donation_support_system.request.BloodInventoryRequest;
//import jakarta.persistence.EntityNotFoundException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class BloodInventoryServiceImp implements BloodInventoryService {
//    @Autowired
//    private BloodInventoryRepository bloodInventoryRepository;
//    @Autowired
//    private HospitalRepository hospitalRepository;
//
//    @Override
//    public BloodInventoryDto updateBloodInventory(BloodInventoryRequest request) {
//        HospitalEntity hospital = hospitalRepository.findById(request.getHospitalId())
//                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with ID: " + request.getHospitalId()));
//
//        BloodInventoryEntity entity = bloodInventoryRepository.findByBloodUnitId(request.getBloodUnitId())
//                .orElse(new BloodInventoryEntity());
//        entity.setHospital(hospital);
//        entity.setBloodUnit(request.getBloodUnitId());
//        entity.setStatus(request.getStatus() != null ? request.getStatus() : BloodInventoryEntity.BloodInventoryStatus.In_Stock);
//        entity.setLastUpdate(LocalDateTime.now());
//        entity = bloodInventoryRepository.save(entity);
//
//        return convertToDto(entity);
//    }
//
//    @Override
//    public List<BloodInventoryDto> getInventoryByHospital(Integer hospitalId) {
//        List<BloodInventoryEntity> entities = bloodInventoryRepository.findByHospitalHospitalId(hospitalId);
//        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BloodInventoryDto> getInventoryByBloodUnitId(Integer bloodUnitId) {
//        Optional<BloodInventoryEntity> entities = bloodInventoryRepository.findByBloodUnitId(bloodUnitId);
//        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    @Override
//    public List<BloodInventoryDto> getAllInventory() {
//        List<BloodInventoryEntity> entities = bloodInventoryRepository.findAll();
//        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
//    }
//
//    private BloodInventoryDto convertToDto(BloodInventoryEntity entity) {
//        BloodInventoryDto dto = new BloodInventoryDto();
////        dto.setInventoryId(entity.getInventoryId());
////        dto.setBloodUnitId(entity.getBloodUnitId());
//
//        dto.setHospitalId(entity.getHospital().getHospitalId());
//        dto.setLastUpdate(entity.getLastUpdate());
//        dto.setStatus(entity.getStatus());
//        return dto;
//    }
//}


package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.BloodInventoryDto;
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

import java.time.LocalDateTime;
import java.util.Collections;
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
        entity.setStatus(request.getStatus() != null ? request.getStatus() : BloodInventoryEntity.BloodInventoryStatus.In_Stock);
        entity.setLastUpdate(LocalDateTime.now());
        entity = bloodInventoryRepository.save(entity);

        return convertToDto(entity);
    }

    @Override
    public List<BloodInventoryDto> getInventoryByHospital(Integer hospitalId) {
        List<BloodInventoryEntity> entities = bloodInventoryRepository.findByHospitalHospitalId(hospitalId);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public List<BloodInventoryDto> getInventoryByBloodUnitId(Integer bloodUnitId) {
        BloodInventoryEntity entity = bloodInventoryRepository.findByBloodUnitBloodUnitId(bloodUnitId)
                .orElseThrow(() -> new EntityNotFoundException("Inventory not found for blood unit ID: " + bloodUnitId));
        return Collections.singletonList(convertToDto(entity));
    }

    @Override
    public List<BloodInventoryDto> getAllInventory() {
        List<BloodInventoryEntity> entities = bloodInventoryRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private BloodInventoryDto convertToDto(BloodInventoryEntity entity) {
        BloodInventoryDto dto = new BloodInventoryDto();
        dto.setInventoryId(entity.getInventoryId());
        dto.setBloodUnitId(entity.getBloodUnit().getBloodUnitId()); // Trả về ID
        dto.setHospitalId(entity.getHospital().getHospitalId());
        dto.setLastUpdate(entity.getLastUpdate());
        dto.setStatus(entity.getStatus());
        return dto;
    }
}