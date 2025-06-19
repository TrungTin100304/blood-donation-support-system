package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.request.UserRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserProfileService {
    ResponseEntity<UserDto> getUserProfile(int userId);

    ResponseEntity<List<UserDto>> getAllUsers();

    ResponseEntity<UserDto> getCurrentUserProfile(String userName);

//    ResponseEntity<String> addUserProfile(UserRequest userRequest, String role);

    ResponseEntity<String> deleteUserProfile(String  userName);

    ResponseEntity<List<UserDto>> searchUsers(String userName);
}
