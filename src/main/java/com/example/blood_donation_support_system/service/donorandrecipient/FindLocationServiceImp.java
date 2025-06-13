package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class FindLocationServiceImp implements FindLocationService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> findNearByDonors(double lat, double lng, double radiusKm) {
        List<UserEntity> users = userRepository.findNearbyDonors(lat, lng, radiusKm);
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : users) {
            UserDto userDto = converToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public List<UserEntity> findNearbyRecipients(double lat, double lng, double radiusKm) {
        return userRepository.findNearbyRecipients(lat, lng, radiusKm);
    }

    private UserDto converToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setName(userEntity.getFullName());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setEmail(userEntity.getEmail());
        userDto.setAddress(userEntity.getAddress());
        userDto.setReadyTime(userEntity.getReadyTime());
        userDto.setGender(userEntity.getGender());
        userDto.setBloodType(userEntity.getBloodType());
        userDto.setYearOfBirth(userEntity.getYearOfBirth());
        return userDto;
    }
}
