package com.example.blood_donation_support_system.dto;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodInventoryDto {
//    private Integer inventoryId;
//    private Integer bloodUnitId;
//    private Integer hospitalId;
//    private LocalDateTime lastUpdate;
//    private BloodInventoryEntity.BloodInventoryStatus status;
private String bloodType; // Thay bloodUnitId
    private String componentType; // Thay bloodUnitId
    private double quantity; // Thay bloodUnitId
    private LocalDate receivedDate; // Thay bloodUnitId
    private LocalDate expiryDate; // Thay bloodUnitId
    private String hospitalName; // Thay hospitalId
    private LocalDateTime lastUpdate; // Giữ lại
    private BloodInventoryEntity.BloodInventoryStatus status; // Thay inventoryId

}
