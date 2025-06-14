package com.example.blood_donation_support_system.request;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BloodInventoryRequest {
    private Integer hospitalId;
    private Integer bloodUnitId;
    private BloodInventoryEntity.BloodInventoryStatus status;
    private double quantity; // Thêm từ BloodUnitEntity
    private LocalDate receivedDate; // Thêm từ BloodUnitEntity
    private LocalDate expiryDate; // Thêm từ BloodUnitEntity

}
