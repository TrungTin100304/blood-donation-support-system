package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.dto.UserDto;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DonationHistoryService {
    List<DonationHistoryDto> getDonationHistoryByUserName(String userName);

    ResponseEntity<List<DonationHistoryDto>> getCurrentUserHistoryDonor(int userId);
    List<DonationHistoryDto> getAllDonationHistoryDtos();
}
