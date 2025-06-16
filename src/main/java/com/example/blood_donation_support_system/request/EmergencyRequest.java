package com.example.blood_donation_support_system.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmergencyRequest {
    private Integer hospitalId;
    private String bloodType;
    private String componentType;
    private Double quantity;
    private String note;
    private LocalDateTime neededTime;
}
