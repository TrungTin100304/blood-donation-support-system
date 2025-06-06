package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.RegisterDonorRequest;
import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.response.UpdateResultResponse;
import com.example.blood_donation_support_system.service.DonorService;
import com.example.blood_donation_support_system.service.UpdateProfileUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private UpdateProfileUserService updateProfileUserService;

    @Autowired
    private DonorService donorService;

    // API để xem hồ sơ người dùng (1 cá nhân)
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable int userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        UserEntity userEntity = userOpt.get();
        UserDto userDto = convertToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }


    // API để xem danh sách tất cả người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')or hasRole('MEMBER')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    // API để cập nhật hồ sơ người dùng
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @PutMapping("/{userName}")
    public ResponseEntity<UpdateResultResponse> updateUserProfile(
            @PathVariable String userName,
            @RequestPart("userRequest") UserRequest userRequest,
            @RequestPart(value = "avatarFile", required = false) MultipartFile avatarFile) {
        UpdateResultResponse response = updateProfileUserService.updateProfile(userName, userRequest, avatarFile);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body(response);
    }

    // API để đăng ký làm người hiến máu, bị trùng với bên DonorController
//    @PostMapping("/register-donor/{userId}")
//    public ResponseEntity<String> registerDonor(
//            @PathVariable int userId,
//            @RequestBody RegisterDonorRequest registerDonorRequest) {
//        try {
//            donorService.registerDonor(userId, registerDonorRequest);
//            return ResponseEntity.ok("Successfully registered as a donor");
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }

    // API để xem lịch sử hiến máu
    @GetMapping("/donation-history/{userId}")
    public ResponseEntity<List<DonationHistoryDto>> getDonationHistory(@PathVariable int userId) {
        List<DonationHistoryEntity> donations = donationHistoryRepository.findByUserUserIdOrderByDonationDateDesc(userId);
        List<DonationHistoryDto> donationDtos = donations.stream()
                .map(this::convertToDonationDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(donationDtos);
    }

    private UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setName(userEntity.getFullName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setAddress(userEntity.getAddress());
        userDto.setBloodType(userEntity.getBloodType());
        userDto.setGender(userEntity.getGender());
        userDto.setYearOfBirth(userEntity.getYearOfBirth());
        userDto.setAvatar(userEntity.getAvatar());
        return userDto;
    }

    private DonationHistoryDto convertToDonationDto(DonationHistoryEntity donation) {
        DonationHistoryDto dto = new DonationHistoryDto();
        dto.setHistoryId(donation.getHistoryId());
        dto.setUserId(donation.getUser().getUserId());
        dto.setDonationDate(donation.getDonationDate());
        Integer bloodUnitId = (donation.getBloodUnitId() != null) ? donation.getBloodUnitId().getBloodUnitId() : null;
        dto.setRecoveryStatus(donation.getRecoveryStatus());
        dto.setRecoveryTime(donation.getRecoveryTime());
        return dto;
    }
}