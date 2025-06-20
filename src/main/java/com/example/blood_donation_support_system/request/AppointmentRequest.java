package com.example.blood_donation_support_system.request;

import lombok.Data;

@Data
public class AppointmentRequest {
    private int appointmentId;
    private String status;
}
