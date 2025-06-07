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

    @Override
    public UserDto getUserById(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);
        if(userEntity.isPresent()){
            return convertUserToUserDto(userEntity.get());
        }else{
            throw  new RuntimeException("User with ID" + id + " not found");
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
        userDto.setAvatar(userEntity.getAvatar());
        return userDto;
    }

}
