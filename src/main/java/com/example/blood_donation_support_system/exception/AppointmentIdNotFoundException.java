package com.example.blood_donation_support_system.exception;

public class AppointmentIdNotFoundException extends RuntimeException {
    public AppointmentIdNotFoundException(String message) {
        super(message);
    }
}
