package com.example.blood_donation_support_system.request;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BloodInventoryRequest {
    private Integer hospitalId;
    private Integer bloodUnitId;
    private BloodInventoryEntity.BloodInventoryStatus status;
    private int quantity; // Thêm từ BloodUnitEntity
    private LocalDate receivedDate; // Thêm từ BloodUnitEntity
    private LocalDate expiryDate; // Thêm từ BloodUnitEntity

}
