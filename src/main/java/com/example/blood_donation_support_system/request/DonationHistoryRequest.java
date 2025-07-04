package com.example.blood_donation_support_system.request;

import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class DonationHistoryRequest {
    private Integer userId;
    private String userName;
    private LocalDateTime donationDate;
    private BloodUnitEntity bloodUnit;
    private int bloodUnitId;
}