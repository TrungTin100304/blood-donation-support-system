//package com.example.blood_donation_support_system.service;
//
//import com.example.blood_donation_support_system.dto.UserDto;
//import com.example.blood_donation_support_system.entity.*;
//import com.example.blood_donation_support_system.repository.BloodInventoryRepository;
//import com.example.blood_donation_support_system.repository.BloodUnitRepository;
//import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
//import com.example.blood_donation_support_system.repository.UserRepository;
//import com.example.blood_donation_support_system.request.BloodInventoryRequest;
//import com.example.blood_donation_support_system.request.DonationHistoryRequest;
//import jakarta.mail.internet.MimeMessage;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.MimeMessageHelper;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import javax.mail.MessagingException;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.temporal.ChronoUnit;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//public class DonationReminderServiceImp implements DonationReminderService {
//
//    private static final Logger logger = LoggerFactory.getLogger(DonationReminderServiceImp.class);
//
//    @Autowired
//    private DonationHistoryRepository donationHistoryRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//
//    @Autowired
//    private JavaMailSender mailSender;
//
//    @Autowired
//    private BloodUnitRepository bloodUnitRepository;
//
//
//    private static final long RECOVERY_DAYS = 56;
//    private static final int PAGE_SIZE = 100;
//
//    @Override
//<<<<<<< HEAD
//    public DonationHistoryEntity recordDonation(DonationHistoryRequest request) {
//        UserEntity user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found..."));
////        if (!checkEligibility(request.getUserId() , request.getDonationDate())) {
////            throw new IllegalArgumentException("User is not eligible...");
////        }
//
//        HospitalEntity hospitalEntity = new HospitalEntity();
//        BloodInventoryEntity bloodInventoryEntity = new BloodInventoryEntity();
//        // Tạo BloodUnitEntity
//        BloodUnitEntity bloodUnit = new BloodUnitEntity();
//        bloodUnit.setBloodType(user.getBloodType());
//        bloodUnit.setComponentType(user.getComponentType()); // Ví dụ
//        bloodUnit.setReceivedDate(request.getDonationDate());
//        bloodUnit.setExpiryDate(request.getDonationDate().plusDays(56));
//        bloodUnit.setUser(user);
//
//        bloodUnitRepository.save(bloodUnit);
//
//        // Ghi nhận lịch sử hiến máu
//        DonationHistoryEntity donation = new DonationHistoryEntity();
//        donation.setUser(user);
//        donation.setDonationDate(request.getDonationDate());
//        donation.setBloodUnit(request.getBloodUnit());  //
//        donation.setRecoveryTime((int) RECOVERY_DAYS);
//        donation.setRecoveryStatus("RECOVERING");
//        donationHistoryRepository.save(donation);
//
//        // Lưu vào kho
//        BloodInventoryRequest inventoryRequest = new BloodInventoryRequest();
//        inventoryRequest.setHospitalId(1); // Bệnh viện mặc định, cần điều chỉnh
//        inventoryRequest.setBloodUnitId(bloodUnit.getBloodUnitId());
////        inventoryRequest.setQuantity(bloodInventoryEntity.getQuantity() + 1);
//        inventoryRequest.setStatus(BloodInventoryEntity.BloodInventoryStatus.IN_STOCK);
//        inventoryRequest.setReceivedDate(request.getDonationDate());
//        inventoryRequest.setExpiryDate(request.getDonationDate().plusDays(42));
//        bloodInventoryService.updateBloodInventory(inventoryRequest);
//
//        return donation;
//    }
//
//=======
////    public DonationHistoryEntity recordDonation(DonationHistoryRequest request) {
////        UserEntity user = userRepository.findById(request.getUserId())
////                .orElseThrow(() -> new IllegalArgumentException("User not found..."));
////        if (!checkEligibility(request.getUserId(), request.getDonationDate())) {
////            throw new IllegalArgumentException("User is not eligible...");
////        }
////        // Tạo BloodUnitEntity
////        BloodUnitEntity bloodUnit = new BloodUnitEntity();
////        bloodUnit.setBloodType(user.getBloodType());
////        bloodUnit.setComponentType(user.getComponentType()); // Ví dụ
////        bloodUnit.setReceivedDate(request.getDonationDate());
////        bloodUnit.setExpiryDate(request.getDonationDate().plusDays(56));
////        bloodUnit.setUser(user);
////        bloodUnitRepository.save(bloodUnit);
////
////        // Ghi nhận lịch sử hiến máu
////        DonationHistoryEntity donation = new DonationHistoryEntity();
////        donation.setUser(user);
////        donation.setDonationDate(request.getDonationDate());
////        donation.setBloodUnit(request.getBloodUnit());  //
////        donation.setRecoveryTime((int) RECOVERY_DAYS);
////        donation.setRecoveryStatus("RECOVERING");
////        donationHistoryRepository.save(donation);
////
////        // Lưu vào kho
////        BloodInventoryRequest inventoryRequest = new BloodInventoryRequest();
////        inventoryRequest.setHospitalId(inventoryRequest.getHospitalId()); // Bệnh viện mặc định, cần điều chỉnh
////        inventoryRequest.setBloodUnitId(bloodUnit.getBloodUnitId());
////        inventoryRequest.setQuantity(inventoryRequest.getQuantity());
////        inventoryRequest.setStatus(BloodInventoryEntity.BloodInventoryStatus.In_Stock);
////        inventoryRequest.setReceivedDate(request.getDonationDate());
////        inventoryRequest.setExpiryDate(request.getDonationDate().plusDays(42));
////        bloodInventoryService.updateBloodInventory(inventoryRequest);
////
////        return donation;
////    }
//    public DonationHistoryEntity recordDonation(DonationHistoryRequest request) {
//        UserEntity user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));
//        LocalDateTime donationDate = request.getDonationDate();
//        if (!checkEligibility(request.getUserId(), donationDate)) {
//            throw new IllegalArgumentException("User is not eligible to donate yet...");
//        }
//        DonationHistoryEntity donation = new DonationHistoryEntity();
//        donation.setUser(user);
//        donation.setDonationDate(request.getDonationDate());
//        Integer bloodUnitId = request.getBloodUnit().getBloodUnitId();
//        if (bloodUnitId == null) {
//            // Tạo mới BloodUnitEntity nếu chưa có
//            BloodUnitEntity bloodUnit = new BloodUnitEntity();
//            bloodUnit.setBloodType(user.getBloodType());
//            bloodUnit.setReceivedDate(donationDate);
//            bloodUnit.setExpiryDate(donationDate.plusDays(42)); // Ví dụ 42 ngày cho RBC
//            bloodUnit.setUser(user);
//            bloodUnit = bloodUnitRepository.save(bloodUnit);
//            donation.setBloodUnit(bloodUnit); // Sử dụng quan hệ đối tượng
//        } else {
//            BloodUnitEntity bloodUnit = bloodUnitRepository.findById(bloodUnitId)
//                    .orElseThrow(() -> new IllegalArgumentException("Blood unit not found with id: " + bloodUnitId));
//            donation.setBloodUnit(bloodUnit);
//        }
//        donation.setRecoveryTime((int) RECOVERY_DAYS);
//        donation.setRecoveryStatus("RECOVERING");
//        return donationHistoryRepository.save(donation);
//    }
//>>>>>>> parent of abe7794 (feat: fix Inventory)
//
//    @Override
//    public boolean checkEligibility(Integer userId, LocalDateTime donationDate) {
//        List<DonationHistoryEntity> donations = donationHistoryRepository.findByUserUserIdOrderByDonationDateDesc(userId);
//        if (donations.isEmpty()) {
//            return true; // Chưa hiến lần nào, đủ điều kiện
//        }
//        DonationHistoryEntity latestDonation = donations.get(0);
//        LocalDate lastDonationDate = latestDonation.getDonationDate().toLocalDate();
//        long daysSinceLastDonation = ChronoUnit.DAYS.between(lastDonationDate, donationDate); // So sánh với ngày hiến mới
//        if (daysSinceLastDonation >= RECOVERY_DAYS) {
//            latestDonation.setRecoveryStatus("ELIGIBLE");
//            donationHistoryRepository.save(latestDonation);
//            return true;
//        }
//        return false;
//    }
//
//    @Override
//    public List<UserDto> getEligibleDonors() {
//        List<UserDto> eligibleDonors = new ArrayList<>();
//        long eligibleCount = donationHistoryRepository.countByRecoveryStatus("ELIGIBLE");
//        if (eligibleCount > 0) {
//            int page = 0;
//            while (true) {
//                Pageable pageable = PageRequest.of(page, PAGE_SIZE);
//                Page<DonationHistoryEntity> eligiblePage = donationHistoryRepository.findByRecoveryStatus("ELIGIBLE", pageable);
//                if (eligiblePage.getContent().isEmpty()) {
//                    break;
//                }
//                eligiblePage.getContent().forEach(donation -> {
//                    UserEntity user = donation.getUser();
//                    UserDto userDto = convertToDto(user);
//                    eligibleDonors.add(userDto);
//                });
//                if (!eligiblePage.hasNext()) {
//                    break;
//                }
//                page++;
//            }
//        }
//
//        List<UserEntity> neverDonatedUsers = userRepository.findUsersWithNoDonationHistory();
//        neverDonatedUsers.forEach(user -> eligibleDonors.add(convertToDto(user)));
//        return eligibleDonors;
//    }
//
//    @Scheduled(cron = "0 0 9 * * ?", zone = "Asia/Ho_Chi_Minh")
//    public void sendDonationReminders() {
//        logger.info("Starting donation reminder task at {}", LocalDateTime.now());
//        List<UserDto> eligibleDonors = getEligibleDonors();
//        for (UserDto donor : eligibleDonors) {
//            if (donor.getEmail() != null && !donor.getEmail().isEmpty()) {
//                sendEmailAsync(donor.getEmail(), donor.getName());
//            }
//        }
//        logger.info("Completed donation reminder task. Notified {} donors", eligibleDonors.size());
//    }
//
//    @Async
//    public void sendEmailAsync(String email, String name) {
//        int retries = 3;
//        boolean sent = false;
//        while (retries > 0 && !sent) {
//            try {
//                sendEmailReminder(email, name);
//                sent = true;
//                logger.info("Email sent successfully to {}", email);
//            } catch (MessagingException e) {
//                retries--;
//                logger.error("Failed to send email to {} (remaining retries: {}): {}", email, retries, e.getMessage());
//                if (retries == 0) {
//                    logger.error("Exhausted retries for sending email to {}. Final error: {}", email, e.getMessage());
//                } else {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException ie) {
//                        Thread.currentThread().interrupt();
//                        logger.warn("Interrupted while waiting to retry email to {}", email);
//                    }
//                }
//            } catch (jakarta.mail.MessagingException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void sendEmailReminder(String email, String name) throws MessagingException, jakarta.mail.MessagingException {
//        MimeMessage message = mailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true);
//        helper.setTo(email);
//        helper.setSubject("Reminder: You Are Eligible to Donate Blood!");
//        helper.setText(String.format(
//                "Dear %s,\n\nYou are now eligible to donate blood again! It has been over 56 days since your last donation. " +
//                        "Please consider scheduling a donation at your nearest hospital.\n\nThank you for your life-saving contribution!\n" +
//                        "Blood Donation Support System",
//                name
//        ));
//        mailSender.send(message);
//    }
//
//    private UserDto convertToDto(UserEntity user) {
//        UserDto userDto = new UserDto();
//        userDto.setName(user.getFullName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPhoneNumber(user.getPhoneNumber());
//        userDto.setAddress(user.getAddress());
//        userDto.setBloodType(user.getBloodType());
//        return userDto;
//    }
//}



package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.BloodUnitRepository;
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
    private BloodUnitRepository bloodUnitRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    private static final int RECOVERY_DAYS = 56; // Số ngày phục hồi
    private static final int PAGE_SIZE = 100;

    @Override
    public DonationHistoryEntity recordDonation(DonationHistoryRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + request.getUserId()));

//        BloodUnitEntity unit = bloodUnitRepository.findById(request.getBloodUnitId()).orElseThrow(() -> new IllegalArgumentException("BloodUnit not found with id: " + request.getBloodUnitId()));

        if (!checkEligibility(request.getUserId(), request.getDonationDate())) {
            throw new IllegalArgumentException("User is not eligible to donate yet. Please wait until recovery period is over.");
        }



        DonationHistoryEntity donation = new DonationHistoryEntity();
        donation.setUser(user);
        donation.setDonationDate(request.getDonationDate());
//        donation.setBloodUnit(request.getBloodUnit().getBloodUnitId()); // chưa lấy được ID, cần fix
        donation.setRecoveryTime(request.getDonationDate().plusDays(RECOVERY_DAYS)); // Đặt ngày phục hồi
        donation.setRecoveryStatus("RECOVERING");

        return donationHistoryRepository.save(donation);
    }

    @Override
    public boolean checkEligibility(Integer userId, LocalDate donationDate) {
        List<DonationHistoryEntity> donations = donationHistoryRepository.findByUserUserIdOrderByDonationDateDesc(userId);
        if (donations.isEmpty()) {
            return true; // Chưa có lịch sử hiến, nên được phép hiến
        }

        DonationHistoryEntity latestDonation = donations.get(0);
        LocalDate recoveryDate = latestDonation.getRecoveryTime(); // Kiểu LocalDate
        LocalDate now = LocalDate.now();

        if (now.isAfter(recoveryDate) || now.isEqual(recoveryDate)) {
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
            } catch (MessagingException | jakarta.mail.MessagingException e) {
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
