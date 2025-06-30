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
public class BloodUnitDto {
    private Integer bloodUnitId;
    private String bloodType;
    private String componentType;
    private int quantity;
    private LocalDate receivedDate;
    private LocalDate expiryDate;
    private Integer userId;
    private String userName;
    private Integer inventoryId;
    private BloodInventoryEntity.BloodInventoryStatus inventoryStatus;
    private String statusUnit;
    private Integer hospitalId;
    private String name;

}
