package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.service.user.GetUserService;
import com.example.blood_donation_support_system.service.user.UserProfileService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class UserProfileController {

    @Autowired
    GetUserService getUserService;

    @Autowired
    private UserProfileService userProfileService;



    @Autowired
    JwtHelper jwtHelper;

    // API để xem hồ sơ người dùng (1 cá nhân)
    @GetMapping("")
    public ResponseEntity<?> getUserProfile(@RequestHeader("Authorization") String userIdHeader) {

        Integer userId = jwtHelper.getUserId(userIdHeader);
        UserDto userDto = getUserService.getUserById(userId);

        if (userDto == null) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("Không tìm thấy thông tin người dùng với ID: " + userId);
        }

        return ResponseEntity.ok(userDto);


    }


    // API để xem hồ sơ người dùng
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserProfile(@PathVariable int userId) {
        return userProfileService.getUserProfile(userId);
    }

    // API để xem danh sách tất cả người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return userProfileService.getAllUsers();
    }

    // API để xem thông tin cá nhân (người dùng đang đăng nhập)
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUserProfile(@RequestHeader("Authorization") String token) {
        String userName = jwtHelper.getUsername(token.replace("Bearer ", ""));
        return userProfileService.getCurrentUserProfile(userName);
    }

    // API để thêm mới hồ sơ người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @PostMapping("/add")
    public ResponseEntity<String> addUserProfile(
            @RequestBody UserRequest userRequest,
            @RequestParam String role) {
        return userProfileService.addUserProfile(userRequest, role);
    }


    // API để xóa hồ sơ người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable int userId) {
        return userProfileService.deleteUserProfile(userId);
    }

    // API để tìm kiếm hồ sơ người dùng theo username hoặc email
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String email) {
        return userProfileService.searchUsers(userName, email);
    }


//    // API để xem danh sách tất cả người dùng
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
//    @GetMapping("/all")
//    public ResponseEntity<List<UserDto>> getAllUsers() {
//        List<UserEntity> users = userRepository.findAll();
//        List<UserDto> userDtos = users.stream()
//                .map(this::convertToDto)
//                .collect(Collectors.toList());
//        return ResponseEntity.ok(userDtos);
//    }
//



//    private UserDto convertToDto(UserEntity userEntity) {
//        UserDto userDto = new UserDto();
//        userDto.setName(userEntity.getFullName());
//        userDto.setEmail(userEntity.getEmail());
//        userDto.setPhoneNumber(userEntity.getPhoneNumber());
//        userDto.setAddress(userEntity.getAddress());
//        userDto.setBloodType(userEntity.getBloodType());
//        userDto.setGender(userEntity.getGender());
//        userDto.setYearOfBirth(userEntity.getYearOfBirth());
//        userDto.setAvatar(userEntity.getAvatar());
//        return userDto;
//    }

//    private DonationHistoryDto convertToDonationDto(DonationHistoryEntity donation) {
//        DonationHistoryDto dto = new DonationHistoryDto();
//        dto.setHistoryId(donation.getHistoryId());
//        dto.setUserId(donation.getUser().getUserId());
//        dto.setDonationDate(donation.getDonationDate());
//        Integer bloodUnitId = (donation.getBloodUnitId() != null) ? donation.getBloodUnitId().getBloodUnitId() : null;
//        dto.setRecoveryStatus(donation.getRecoveryStatus());
//        dto.setRecoveryTime(donation.getRecoveryTime());
//        return dto;
//    }
}