package com.example.blood_donation_support_system.utils;

import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor // tự tạo constructor cho RecoveryStatusScheduler
public class RecoveryStatusScheduler {

    private final DonationHistoryRepository donationHistoryRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Chạy mỗi ngày lúc 00:00
    public void updateRecoveryStatus() {
        List<DonationHistoryEntity> recoveringList =
                donationHistoryRepository.findByRecoveryStatus("RECOVERING");

        LocalDateTime now = LocalDateTime.now();

        for (DonationHistoryEntity history : recoveringList) {
            if (history.getCreatedAt().plusDays(7).isBefore(now)) {
                history.setRecoveryStatus("RECOVERED");
            }
        }

        donationHistoryRepository.saveAll(recoveringList);
    }
}
