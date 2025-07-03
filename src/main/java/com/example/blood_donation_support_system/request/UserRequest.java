package com.example.blood_donation_support_system.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private String gender;
    private LocalDate yearOfBirth;
    private String bloodType;
    private double latitude;
    private double longitude;
    private String otp;
    private String newPassword;
}
