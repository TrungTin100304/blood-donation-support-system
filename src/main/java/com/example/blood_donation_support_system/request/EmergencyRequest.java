package com.example.blood_donation_support_system.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmergencyRequest {
    private String bloodType;
    private int quantity;
    private String hospitalName;
    private String note;
    private LocalDateTime neededTime;
    private String componentType;
}
