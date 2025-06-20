package com.example.blood_donation_support_system.dto;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleBloodInventoryDto {
    private Integer inventoryId;
    private String bloodType;
    private String componentType;
    private String hospitalName;
    private String hospitalAddress;
    private String status;
}
