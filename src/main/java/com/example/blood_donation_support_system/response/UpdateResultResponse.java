package com.example.blood_donation_support_system.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateResultResponse {
    private boolean success;
    private String message;

}
