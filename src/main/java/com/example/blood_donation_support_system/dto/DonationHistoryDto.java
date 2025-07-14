package com.example.blood_donation_support_system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonationHistoryDto {
    private int historyId;
    private int userId;
    private String userName;
    private LocalDateTime donationDate;
    private Integer bloodUnitId;
    private String bloodType;
    private String recoveryStatus;
    private LocalDateTime recoveryTime;
}
