package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.request.RoleRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
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
    public ResponseEntity<?> getUserProfile(@PathVariable int userId) {

        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Hồ sơ của User có ID: " + userId);
        response.setData(userProfileService.getUserProfile(userId));
        return ResponseEntity.ok(response);

    }

    // API để xem danh sách tất cả người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    @GetMapping("/all")
    public ResponseEntity<?> getAllUsers() {

        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Hồ sơ tất cả User" );
        response.setData(userProfileService.getAllUsers());
        return ResponseEntity.ok(response);

    }

    // API để xem thông tin cá nhân (người dùng đang đăng nhập)
    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUserProfile(@RequestHeader("Authorization") String token) {
        String userName = jwtHelper.getUsername(token.replace("Bearer ", ""));

        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Hồ sơ của bạn: " + userName );
        response.setData(userProfileService.getCurrentUserProfile(userName));
        return ResponseEntity.ok(response);

    }


    // API để xóa hồ sơ người dùng
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUserProfile(@PathVariable Integer userId) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Hồ sơ của bạn có UserId : " + userId + " đã DELETED" );
        response.setData(userProfileService.deleteUserProfile(userId));
        return ResponseEntity.ok(response);

    }

    // API để tìm kiếm hồ sơ người dùng theo username
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @GetMapping("/search")
    public ResponseEntity<?> searchUsers(
            @RequestParam String userName) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Hồ sơ của  : " + userName );
        response.setData(userProfileService.searchUsers(userName));
        return ResponseEntity.ok(response);

    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('MEMBER')")
    @GetMapping("/search-gender")
    public ResponseEntity<?> searchGender(
            @RequestParam String gender) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Hồ sơ của bạn có Gender : " + gender  );
        response.setData(userProfileService.searchGender(gender));
        return ResponseEntity.ok(response);

    }


    @PostMapping("/edit-role")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> updateUserRole(@RequestBody RoleRequest request) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Cập nhật Role của User có ID : " +  request.getUserId());
        response.setData(userProfileService.updateUserRole(request.getUserId(), request.getRoleName()));
        return ResponseEntity.ok(response);

    }


    @GetMapping("/history-donor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> getDonationHistory(@RequestParam String userName) {
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Lịch sử hiến máu của : " + userName );
        response.setData(donationHistoryService.getDonationHistoryByUserName(userName));
        return ResponseEntity.ok(response);
//        List<DonationHistoryDto> histories = donationHistoryService.getDonationHistoryByUserName(userName);
//        return ResponseEntity.ok(histories);
    }


    @GetMapping("/history-donor/me")
    public ResponseEntity<?> getCurrentUserHistoryDonor(@RequestHeader("Authorization") String token) {
        try {
            // Lấy userId từ token
            String jwtToken = token.replace("Bearer ", "");
            int userId = jwtHelper.getUserId(jwtToken);
            BaseResponse response = new BaseResponse();
            response.setCode(200);
            response.setMessage("Lịch sử hiến máu của bạn : " );
            response.setData(donationHistoryService.getCurrentUserHistoryDonor(userId));
            return ResponseEntity.ok(response);
            // Gọi service và trả về kết quả trực tiếp
//            return donationHistoryService.getCurrentUserHistoryDonor(userId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token: " + e.getMessage());
        }
    }


    @GetMapping("/history/all")
    public ResponseEntity<?> getAllDonationHistory() {
//        List<DonationHistoryDto> histories = donationHistoryService.getAllDonationHistoryDtos();
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setMessage("Lịch sử hiến máu của tất cả User : " );
        response.setData(donationHistoryService.getAllDonationHistoryDtos());
        return ResponseEntity.ok(response);
//        return ResponseEntity.ok(histories);
    }


}