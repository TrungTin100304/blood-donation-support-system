package com.example.blood_donation_support_system.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
public class RegisterDonorRequest {
    private String bloodType;
    private LocalDateTime readyTime;
    private Integer hospitalId;
    private String componentType;
    private double latitude;
    private double longitude;
    private String note;
}
