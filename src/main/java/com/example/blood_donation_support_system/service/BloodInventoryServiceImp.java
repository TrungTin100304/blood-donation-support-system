package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.BloodInventoryDto;
import com.example.blood_donation_support_system.dto.SimpleBloodInventoryDto;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BloodInventoryServiceImp implements BloodInventoryService {
    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Autowired
    private BloodUnitRepository bloodUnitRepository;

    @Override
    public BloodInventoryDto updateBloodInventory(BloodInventoryRequest request) {
        HospitalEntity hospital = hospitalRepository.findById(request.getHospitalId())
                .orElseThrow(() -> new EntityNotFoundException("Hospital not found with ID: " + request.getHospitalId()));

        BloodUnitEntity bloodUnit = bloodUnitRepository.findById(request.getBloodUnitId()).orElseThrow(() -> new EntityNotFoundException("BloodUnit not found with ID: " + request.getBloodUnitId()));


        BloodInventoryEntity entity = bloodInventoryRepository.findByBloodUnit(bloodUnit)
                .orElse(new BloodInventoryEntity());
        entity.setHospital(hospital);
        entity.setBloodUnit(bloodUnit);
        entity.setStatus(request.getStatus() != null ? request.getStatus() : BloodInventoryEntity.BloodInventoryStatus.IN_STOCK);
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
        BloodUnitEntity bloodUnit = bloodUnitRepository.findById(bloodUnitId)
                .orElseThrow(() -> new EntityNotFoundException("BloodUnit not found with ID: " + bloodUnitId));
        Optional<BloodInventoryEntity> entities = bloodInventoryRepository.findByBloodUnit(bloodUnit);
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private BloodInventoryDto convertToDto(BloodInventoryEntity entity) {
        BloodInventoryDto dto = new BloodInventoryDto();
        dto.setInventoryId(entity.getInventoryId());
        dto.setBloodUnitId(entity.getBloodUnit().getBloodUnitId());
        dto.setHospitalId(entity.getHospital().getHospitalId());
        dto.setLastUpdate(entity.getLastUpdate());
        return dto;
    }


//    //Tìm đơn vị máu phù hợp trong blood_inventory có status = 'IN_STOCK'
//    @Override
//    public List<SimpleBloodInventoryDto> findAvailableBloodUnit(String bloodType, String componentType) {
//        List<BloodInventoryEntity> inventoryBloodUnit = bloodInventoryRepository.findMatchingInventory(bloodType, componentType);
//        List<SimpleBloodInventoryDto> bloodInventoryDtoList = new ArrayList<>();
//        for(BloodInventoryEntity entity : inventoryBloodUnit){
//            SimpleBloodInventoryDto bloodInventoryDto = simpleConvertToDto(entity);
//            bloodInventoryDtoList.add(bloodInventoryDto);
//        }
//        return bloodInventoryDtoList;
//    }
//
//    //Cập nhật blood_inventory.status = 'USED'
//
//    @Override
//    public Boolean markInventoryUsed(Integer inventoryId, BloodInventoryRequest bloodInventoryRequest) {
//        Optional<BloodInventoryEntity> findById = bloodInventoryRepository.findById(inventoryId);
//        if(findById.isPresent()){
//            BloodInventoryEntity bloodInventoryEntity = findById.get();
//            bloodInventoryEntity.setStatus(bloodInventoryRequest.getStatus());
//            bloodInventoryRepository.save(bloodInventoryEntity);
//            return true;
//        }
//        return false;
//    }

    private SimpleBloodInventoryDto simpleConvertToDto(BloodInventoryEntity bloodInventoryEntity) {
        SimpleBloodInventoryDto dto = new SimpleBloodInventoryDto();
        dto.setInventoryId(bloodInventoryEntity.getInventoryId());
        dto.setBloodType(bloodInventoryEntity.getBloodUnit().getBloodType());
        dto.setComponentType(bloodInventoryEntity.getBloodUnit().getComponentType());
        dto.setHospitalName(bloodInventoryEntity.getHospital().getName());
        dto.setHospitalAddress(bloodInventoryEntity.getHospital().getAddress());
        dto.setStatus(bloodInventoryEntity.getStatus().name());
        return dto;


    }


}