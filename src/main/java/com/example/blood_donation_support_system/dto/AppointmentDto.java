package com.example.blood_donation_support_system.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class AppointmentDto {
    private int appointmentId;
    private String donorName;
    private String recipientName;
    private String location;
    private String status;
    private LocalDate appointmentDate;
}
