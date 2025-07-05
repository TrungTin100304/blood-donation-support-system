package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.request.RegisterDonorRequest;

import java.time.LocalDate;
import java.util.List;

public interface DonorService {
    void registerDonor(int userId, RegisterDonorRequest registerDonorRequest);
    UserDto getDonorById(Integer id);
//    void sendDonationReminderEmail(String authHeader, String toEmail, LocalDate lastDonationDate);
}
