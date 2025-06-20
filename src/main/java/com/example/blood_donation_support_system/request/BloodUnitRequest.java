package com.example.blood_donation_support_system.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BloodUnitRequest {
    private String bloodType;
    private String componentType;
}
