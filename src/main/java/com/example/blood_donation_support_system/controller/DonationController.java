//package com.example.blood_donation_support_system.controller;
//
//import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
//import com.example.blood_donation_support_system.request.DonationHistoryRequest;
//import com.example.blood_donation_support_system.service.DonationReminderService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/donation")
//public class DonationController {
//
//    @Autowired
//    private DonationReminderService donationReminderService;
//
//    @PostMapping("/record")
//    public ResponseEntity<String> recordDonation(@RequestBody DonationHistoryRequest request) {
//        try {
//            DonationHistoryEntity donation = donationReminderService.recordDonation(request);
//            return ResponseEntity.ok("Donation recorded successfully with ID: " + donation.getHistoryId());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}