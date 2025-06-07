package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.DonationHistoryRequest;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class DonationReminderServiceImp implements DonationReminderService {

    private static final Logger logger = LoggerFactory.getLogger(DonationReminderServiceImp.class);

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final long RECOVERY_DAYS = 56;
    private static final int PAGE_SIZE = 100;

    @Override
    public DonationHistoryEntity recordDonation(DonationHistoryRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));

        if (!checkEligibility(request.getUserId())) {
            throw new IllegalArgumentException("User is not eligible to donate yet. Please wait until recovery period is over.");
        }

        DonationHistoryEntity donation = new DonationHistoryEntity();
        donation.setUser(user);
        donation.setDonationDate(request.getDonationDate());
        donation.setBloodUnitId(request.getBloodUnit());
        donation.setRecoveryTime((int) RECOVERY_DAYS);
        donation.setRecoveryStatus("RECOVERING");
        return donationHistoryRepository.save(donation);
    }

    @Override
    public boolean checkEligibility(Integer userId) {
        List<DonationHistoryEntity> donations = donationHistoryRepository.findByUserUserIdOrderByDonationDateDesc(userId);
        if (donations.isEmpty()) {
            return true;
        }

        DonationHistoryEntity latestDonation = donations.get(0);
        LocalDate lastDonationDate = latestDonation.getDonationDate().toLocalDate();
        LocalDate now = LocalDate.now();
        long daysSinceLastDonation = ChronoUnit.DAYS.between(lastDonationDate, now);

        if (daysSinceLastDonation >= RECOVERY_DAYS) {
            latestDonation.setRecoveryStatus("ELIGIBLE");
            donationHistoryRepository.save(latestDonation);
            return true;
        }
        return false;
    }

    @Override
    public List<UserDto> getEligibleDonors() {
        List<UserDto> eligibleDonors = new ArrayList<>();
        long eligibleCount = donationHistoryRepository.countByRecoveryStatus("ELIGIBLE");
        if (eligibleCount > 0) {
            int page = 0;
            while (true) {
                Pageable pageable = PageRequest.of(page, PAGE_SIZE);
                Page<DonationHistoryEntity> eligiblePage = donationHistoryRepository.findByRecoveryStatus("ELIGIBLE", pageable);
                if (eligiblePage.getContent().isEmpty()) {
                    break;
                }
                eligiblePage.getContent().forEach(donation -> {
                    UserEntity user = donation.getUser();
                    UserDto userDto = convertToDto(user);
                    eligibleDonors.add(userDto);
                });
                if (!eligiblePage.hasNext()) {
                    break;
                }
                page++;
            }
        }

        List<UserEntity> neverDonatedUsers = userRepository.findUsersWithNoDonationHistory();
        neverDonatedUsers.forEach(user -> eligibleDonors.add(convertToDto(user)));
        return eligibleDonors;
    }

    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Ho_Chi_Minh")
    public void sendDonationReminders() {
        logger.info("Starting donation reminder task at {}", LocalDateTime.now());
        List<UserDto> eligibleDonors = getEligibleDonors();
        for (UserDto donor : eligibleDonors) {
            if (donor.getEmail() != null && !donor.getEmail().isEmpty()) {
                sendEmailAsync(donor.getEmail(), donor.getName());
            }
        }
        logger.info("Completed donation reminder task. Notified {} donors", eligibleDonors.size());
    }

    @Async
    public void sendEmailAsync(String email, String name) {
        int retries = 3;
        boolean sent = false;
        while (retries > 0 && !sent) {
            try {
                sendEmailReminder(email, name);
                sent = true;
                logger.info("Email sent successfully to {}", email);
            } catch (MessagingException e) {
                retries--;
                logger.error("Failed to send email to {} (remaining retries: {}): {}", email, retries, e.getMessage());
                if (retries == 0) {
                    logger.error("Exhausted retries for sending email to {}. Final error: {}", email, e.getMessage());
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        logger.warn("Interrupted while waiting to retry email to {}", email);
                    }
                }
            } catch (jakarta.mail.MessagingException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void sendEmailReminder(String email, String name) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(email);
        helper.setSubject("Reminder: You Are Eligible to Donate Blood!");
        helper.setText(String.format(
                "Dear %s,\n\nYou are now eligible to donate blood again! It has been over 56 days since your last donation. " +
                        "Please consider scheduling a donation at your nearest hospital.\n\nThank you for your life-saving contribution!\n" +
                        "Blood Donation Support System",
                name
        ));
        mailSender.send(message);
    }

    private UserDto convertToDto(UserEntity user) {
        UserDto userDto = new UserDto();
        userDto.setName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setAddress(user.getAddress());
        userDto.setBloodType(user.getBloodType());
        return userDto;
    }
}