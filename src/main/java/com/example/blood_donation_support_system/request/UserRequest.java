package com.example.blood_donation_support_system.request;

import lombok.Data;

@Data
public class UserRequest {
    private String userName;
    private String password;
    private String fullName;

}
