package com.example.blood_donation_support_system.request;

import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import lombok.Data;

@Data
public class BloodInventoryRequest {
    private Integer hospitalId;
    private Integer bloodUnitId;
    private BloodInventoryEntity.BloodInventoryStatus status;

}
