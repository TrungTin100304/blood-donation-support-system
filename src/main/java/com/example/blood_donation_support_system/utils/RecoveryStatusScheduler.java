package com.example.blood_donation_support_system.utils;

import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import com.example.blood_donation_support_system.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Component
@RequiredArgsConstructor // tự tạo constructor cho RecoveryStatusScheduler
public class RecoveryStatusScheduler {

    private final DonationHistoryRepository donationHistoryRepository;

    private final EmailService emailService;

    @Scheduled(cron = "0 * * * * ?") // Chạy mỗi ngày lúc 00:00
    public void updateRecoveryStatus() {
        List<DonationHistoryEntity> recoveringList =
                donationHistoryRepository.findByRecoveryStatus("Recovering");

        LocalDateTime now = LocalDateTime.now();

        for (DonationHistoryEntity history : recoveringList) {
            if (history.getCreatedAt().plusMinutes(1).isBefore(now)) {

                String email = history.getUser().getEmail().trim();
                String fullName = history.getUser().getFullName().trim();
                if (email != null && fullName != null) {
                    email = email.trim();
                    fullName = fullName.trim();

                    if (!email.isBlank()) {
                        LocalDate ngayHienGanNhat = history.getCreatedAt().toLocalDate();
                        emailService.sendDonationReminder(fullName, email, ngayHienGanNhat);
                        history.setRecoveryStatus("Recovered");
                    }
                }
            }

            donationHistoryRepository.saveAll(recoveringList);
        }
    }
}

