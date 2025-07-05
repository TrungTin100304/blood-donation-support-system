package com.example.blood_donation_support_system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String email;
    private String name;
    private String avatar;
    private String sub;
    private String phoneNumber;
    private String address;
    private String bloodType;
    private LocalDateTime readyTime;
    private String gender;
    private LocalDate yearOfBirth;
    private int userId;
    private String status;
    private String roleName;

}


