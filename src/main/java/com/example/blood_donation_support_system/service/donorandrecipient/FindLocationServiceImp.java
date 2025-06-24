package com.example.blood_donation_support_system.service.donorandrecipient;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.exception.UserIdNotFoundException;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class FindLocationServiceImp implements FindLocationService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> findNearByDonors(double lat, double lng, double radiusKm, String bloodType , Integer userId) {
        UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserIdNotFoundException("User Id not found"));
        userEntity.setLatitude(lat);
        userEntity.setLongitude(lng);
        userRepository.save(userEntity);
        List<String> compatibleBloodType = getCompatibleBloodTypesForWholeBlood(bloodType);
        List<UserEntity> users = userRepository.findNearbyDonors(lat, lng, radiusKm, compatibleBloodType);
        List<UserDto> userDtos = new ArrayList<>();
        for (UserEntity user : users) {
            if(user.getUserId() == userId) {continue;}
            UserDto userDto = convertToDto(user);
            userDtos.add(userDto);
        }
        return userDtos;
    }

    private List<String> getCompatibleBloodTypesForWholeBlood(String bloodType){
        Map<String, List<String>> map = new HashMap<>();
        map.put("A+", List.of("A+", "A-", "O+", "O-"));
        map.put("A-", List.of("A-", "O-"));
        map.put("B+", List.of("B+", "B-", "O+", "O-"));
        map.put("B-", List.of("B-", "O-"));
        map.put("AB+", List.of("A+", "A-", "B+", "B-", "AB+", "AB-", "O+", "O-"));
        map.put("AB-", List.of("A-", "B-", "AB-", "O-"));
        map.put("O+", List.of("O+", "O-"));
        map.put("O-", List.of("O-"));
        map.put("", List.of(""));
        return map.getOrDefault(bloodType, new ArrayList<>());

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
