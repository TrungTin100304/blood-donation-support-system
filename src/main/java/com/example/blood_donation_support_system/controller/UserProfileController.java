package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.request.RoleRequest;
import com.example.blood_donation_support_system.service.user.DonationHistoryService;
import com.example.blood_donation_support_system.service.donorandrecipient.GetUserService;
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
    private DonationHistoryService donationHistoryService;

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

//    // API để thêm mới hồ sơ người dùng
//    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
//    @PostMapping("/add")
//    public ResponseEntity<String> addUserProfile(
//            @RequestBody UserRequest userRequest,
//            @RequestParam String role) {
//        return userProfileService.addUserProfile(userRequest, role);
//    }


    // API để xóa hồ sơ người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUserProfile(@PathVariable Integer userId) {
        return userProfileService.deleteUserProfile(userId);
    }

    // API để tìm kiếm hồ sơ người dùng theo username
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> searchUsers(
            @RequestParam String userName) {
        return userProfileService.searchUsers(userName);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @GetMapping("/search-gender")
    public ResponseEntity<List<UserDto>> searchGender(
            @RequestParam String gender) {
        return userProfileService.searchGender(gender);
    }


    @PutMapping("/{userId}/role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> updateUserRole(@RequestBody RoleRequest request) {
        return userProfileService.updateUserRole(request.getUserId(), request.getRoleName() );
    }


    @GetMapping("/history-donor")
    public ResponseEntity<List<DonationHistoryDto>> getDonationHistory(@RequestParam String userName) {
        List<DonationHistoryDto> histories = donationHistoryService.getDonationHistoryByUserName(userName);
        return ResponseEntity.ok(histories);
    }


    @GetMapping("/history/all")
    public ResponseEntity<List<DonationHistoryDto>> getAllDonationHistory() {
        List<DonationHistoryDto> histories = donationHistoryService.getAllDonationHistoryDtos();
        return ResponseEntity.ok(histories);
    }


}