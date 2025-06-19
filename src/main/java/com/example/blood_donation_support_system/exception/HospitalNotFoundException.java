package com.example.blood_donation_support_system.exception;

public class HospitalNotFoundException extends RuntimeException{
    public HospitalNotFoundException(String message) {
        super(message);
    }
}
