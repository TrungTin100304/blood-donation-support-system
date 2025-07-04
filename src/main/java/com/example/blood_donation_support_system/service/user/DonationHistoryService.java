package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;

import java.util.List;

public interface DonationHistoryService {
    List<DonationHistoryDto> getDonationHistoryByUserName(String userName);

    List<DonationHistoryDto> getAllDonationHistoryDtos();
}
