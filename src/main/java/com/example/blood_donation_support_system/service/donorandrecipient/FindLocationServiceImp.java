package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.exception.UserIdNotFoundException;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class FindLocationServiceImp implements FindLocationService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> findNearByDonors(double lat, double lng, double radiusKm , Integer userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserIdNotFoundException("User Id not found"));
        userEntity.setLatitude(lat);
        userEntity.setLongitude(lng);
        userRepository.save(userEntity);
        List<UserEntity> users = userRepository.findNearbyDonors(lat, lng, radiusKm);
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : users) {
            if(user.getUserId() == userId) {continue;}
            UserDto userDto = convertToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    @Override
    public List<UserDto> findUsersNearByHospital( double radiusKm, Long hospitalId) {
        List<UserEntity> userEntities = userRepository.findActiveUsersNearHospital( hospitalId, radiusKm);
        if(userEntities.isEmpty()) {
            System.out.println("No users found near hospital");
            return Collections.emptyList();
        }
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {
            UserDto userDto = convertToDto(userEntity);
            userDtos.add(userDto);
        }
        return userDtos;
    }


    private UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setUserId(userEntity.getUserId());
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
