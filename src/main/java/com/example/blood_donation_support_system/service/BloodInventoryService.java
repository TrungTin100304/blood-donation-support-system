package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.BloodInventoryDto;
import com.example.blood_donation_support_system.request.BloodInventoryRequest;

import java.util.List;

public interface BloodInventoryService {
    BloodInventoryDto updateBloodInventory(BloodInventoryRequest request);

    List<BloodInventoryDto> getInventoryByHospital(Integer hospitalId);

    List<BloodInventoryDto> getInventoryByBloodUnitId(Integer bloodUnitId);


    List<BloodInventoryDto> getAllInventory();
}