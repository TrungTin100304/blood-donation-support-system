package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.DonorDto;
import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GetUserServiceImp implements GetUserService {
    @Autowired
    private UserRepository userRepository;

    private final String BASE_URL = "http://localhost:8080/upload/useravatars/";

    @Override
    public UserDto getUserById(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            return convertUserToUserDto(userEntity.get());
        } else {
            throw new RuntimeException("User with ID " + id + " not found");
        }
    }

    @Override
    public List<DonorDto> getAllDonors() {
        List<UserEntity> donors = userRepository.findByDonationStatus("Pending");
        List<DonorDto> donorDtos = donors.stream()
                .map(this::convertUserEntityToUserDto)
                .toList();
        return donorDtos;

    }

    public DonorDto convertUserEntityToUserDto(UserEntity userEntity) {
        DonorDto donorDto = new DonorDto();
        donorDto.setDonorId(userEntity.getUserId());
        donorDto.setName(userEntity.getFullName());
        donorDto.setBloodType(userEntity.getBloodType());
        donorDto.setGender(userEntity.getGender());
        donorDto.setReadyTime(userEntity.getReadyTime());
        return donorDto;
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
        userDto.setUserId(userEntity.getUserId());

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
