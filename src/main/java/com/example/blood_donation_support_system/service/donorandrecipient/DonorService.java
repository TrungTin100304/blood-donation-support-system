package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.request.RegisterDonorRequest;

import java.time.LocalDate;

public interface DonorService {
    void registerDonor(int userId, RegisterDonorRequest registerDonorRequest);
//    void sendDonationReminderEmail(String authHeader, String toEmail, LocalDate lastDonationDate);
}
