package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.request.RegisterDonorRequest;

public interface DonorService {
    void registerDonor(int userId, RegisterDonorRequest registerDonorRequest);
}
