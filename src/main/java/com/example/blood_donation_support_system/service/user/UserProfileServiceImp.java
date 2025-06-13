package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.RoleEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.BloodUnitRepository;
import com.example.blood_donation_support_system.repository.RoleRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProfileServiceImp implements UserProfileService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BloodUnitRepository bloodUnitRepository;


    @Override
    public ResponseEntity<UserDto> getUserProfile(int userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (!userOpt.isPresent()) {
            return ResponseEntity.badRequest().body(null);
        }
        UserEntity userEntity = userOpt.get();
        UserDto userDto = convertToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserEntity> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userDtos);
    }

    @Override
    public ResponseEntity<UserDto> getCurrentUserProfile(String userName) {
        UserEntity userEntity = userRepository.findByUserName(userName)
                .orElse(null);
        if (userEntity == null) {
            return ResponseEntity.badRequest().body(null);
        }
        UserDto userDto = convertToDto(userEntity);
        return ResponseEntity.ok(userDto);
    }

    // chua fix add
    @Override
    public ResponseEntity<String> addUserProfile(UserRequest userRequest, String role) {
        try {
            if (userRepository.findByUserName(userRequest.getUserName()) != null) {
                return ResponseEntity.badRequest().body("Username already exists");
            }


            Optional<RoleEntity> roleEntityOpt = roleRepository.findByRoleName(role);
            if (!roleEntityOpt.isPresent()) {
                return ResponseEntity.badRequest().body("Role not found");
            }

            RoleEntity roleEntity = roleEntityOpt.get();
            UserEntity userEntity = new UserEntity();
            userEntity.setUserName(userRequest.getUserName()); // Sử dụng email làm username tạm thời
            userEntity.setPassword(userRequest.getPassword()); // Sử dụng phoneNumber làm password tạm thời
            userEntity.setFullName(userRequest.getFullName()); // Sử dụng address làm fullName tạm thời
            userEntity.setEmail(userRequest.getEmail());
            userEntity.setPhoneNumber(userRequest.getPhoneNumber());
            userEntity.setAddress(userRequest.getAddress());
//            userEntity.setRoleEntity(roleEntity.getRoleID());
            userRepository.save(userEntity);
            return ResponseEntity.ok("User added successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<String> deleteUserProfile(String userName) {
        try {
            Optional<UserEntity> userOpt = userRepository.findByUserName(userName);
            if (!userOpt.isPresent()) {
                return ResponseEntity.badRequest().body("User not found");
            }

            UserEntity user = userOpt.get();
//            if (bloodUnitRepository.existsByUserId_UserId(userId)) {
//                return ResponseEntity.badRequest().body("Cannot delete user: User has associated blood units that may be needed for emergency contact");
//            }

            user.setStatus("DELETED");
            userRepository.save(user); // Cập nhật thay vì xóa
            return ResponseEntity.ok("User marked as deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to delete user: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<List<UserDto>> searchUsers(String userName) {
        List<UserEntity> users = new ArrayList<>();
         if (userName != null && !userName.isEmpty()) {
            users = userRepository.findByUserNameContainingIgnoreCase(userName);
        }
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
        userDto.setReadyTime(userEntity.getReadyTime());
        userDto.setStatus(userEntity.getStatus());
        return userDto;
    }
}
