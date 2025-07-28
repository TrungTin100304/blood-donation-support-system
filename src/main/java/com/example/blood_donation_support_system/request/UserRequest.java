package com.example.blood_donation_support_system.request;

import com.example.blood_donation_support_system.service.user.Adult;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String fullName;
    @Pattern(regexp = "^\\d{9,15}$", message = "Invalid phone number")
    private String phoneNumber;
    @Email(message = "Invalid email format")
    private String email;
    private String address;
    private String gender;
    @NotNull(message = "Year of birth is required")
    @Adult(message = "You must be at least 18 years old!")
    private LocalDate yearOfBirth;
    private String bloodType;
    private double latitude;
    private double longitude;
    private String otp;
    private String newPassword;
}
