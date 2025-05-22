package com.example.blood_donation_support_system.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String name;
    private String avatar;
    private String sub;
}


