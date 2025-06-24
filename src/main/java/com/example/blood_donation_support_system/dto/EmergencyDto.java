package com.example.blood_donation_support_system.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmergencyDto {
    private int emergencyId;
    private String receiver;
    private String bloodType;
    private String componentType;
    private int quantity;
    private String hospitalName;
    private String note;
    private LocalDateTime needTime;
    private LocalDateTime createAt;
    private String status;
}
