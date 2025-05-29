package com.example.blood_donation_support_system.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonationHistoryRequest {
    private Integer userId;
    private LocalDateTime donationDate;
    private Integer bloodUnitId;
}