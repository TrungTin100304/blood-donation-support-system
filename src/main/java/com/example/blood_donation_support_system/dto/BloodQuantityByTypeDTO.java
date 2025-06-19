package com.example.blood_donation_support_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodQuantityByTypeDTO {
//    private String hospitalname;
    private String bloodType;
    private Integer totalQuantity;


}