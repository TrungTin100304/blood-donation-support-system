package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetUserServiceImp implements GetUserService {
    @Autowired
    private UserRepository userRepository;

    private final String BASE_URL = "http://localhost:8080/upload/user-avatars/";

    @Override
    public UserDto getUserById(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            return convertUserToUserDto(userEntity.get());
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

    public UserDto convertUserToUserDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();

        userDto.setName(userEntity.getFullName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setGender(userEntity.getGender());
        userDto.setYearOfBirth(userEntity.getYearOfBirth());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setAddress(userEntity.getAddress());
        userDto.setBloodType(userEntity.getBloodType());

        // Xử lý avatar
        String avatarPath = userEntity.getAvatar(); // ví dụ: "./upload/user-avatars/hinh-nen-gai-xinh.jpg"
        if (avatarPath != null && !avatarPath.isEmpty()) {
            String fileName = avatarPath.substring(avatarPath.lastIndexOf("/") + 1);
            System.out.println(fileName);
            String avatarUrl = BASE_URL + fileName;
            userDto.setAvatar(avatarUrl);
        } else {
            userDto.setAvatar(null); // hoặc ảnh mặc định nếu bạn muốn
        }

        return userDto;
    }

}
