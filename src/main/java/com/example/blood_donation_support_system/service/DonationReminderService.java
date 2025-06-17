package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.request.DonationHistoryRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface DonationReminderService {
    DonationHistoryEntity recordDonation(DonationHistoryRequest request);
    boolean checkEligibility(Integer userId, LocalDateTime donationDate);
    List<UserDto> getEligibleDonors();
}