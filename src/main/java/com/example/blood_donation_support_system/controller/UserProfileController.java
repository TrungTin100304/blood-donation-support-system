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
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
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