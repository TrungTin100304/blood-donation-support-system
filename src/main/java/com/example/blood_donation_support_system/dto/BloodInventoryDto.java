package com.example.blood_donation_support_system.dto;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodInventoryDto {
    private Integer inventoryId;
    private Integer bloodUnitId;
    private Integer hospitalId;
    private LocalDateTime lastUpdate;
    private BloodInventoryEntity.BloodInventoryStatus status;

}
