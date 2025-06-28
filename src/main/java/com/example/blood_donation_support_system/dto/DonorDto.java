package com.example.blood_donation_support_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DonorDto {
    private int donorId;
    private String name;
    private String bloodType;
    private String gender;
    private LocalDateTime readyTime;

}
