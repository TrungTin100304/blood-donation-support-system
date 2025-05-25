package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BloodMatchingServiceImp implements BloodMatchingService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> findCompatibleDonor(String recipentBloodType) {
        // chuẩn hóa đầu vào
        recipentBloodType = recipentBloodType.trim().toUpperCase();

        List<String> compatibleTypes = getCompatibleBloodTypes(recipentBloodType);
        List<UserEntity> userEntities = userRepository.findByBloodTypeIn(compatibleTypes);

        List<UserDto> userDtos = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            UserDto userDto = this.convertToDto(userEntity);
            userDtos.add(userDto);
        }
        return userDtos;

        //return userEntities.stream()
        //        .map(this::convertToDto)
        //        .collect(Collectors.toList());
        //return userEntities.stream()

    }

    private List<String> getCompatibleBloodTypes(String recipientBloodType) {
        Map<String, List<String>> compatibilityMap = new HashMap<>();
        compatibilityMap.put("O-", List.of("O-"));
        compatibilityMap.put("O+", List.of("O-", "O+"));
        compatibilityMap.put("A-", List.of("O-", "A-"));
        compatibilityMap.put("A+", List.of("O-", "O+", "A-", "A+"));
        compatibilityMap.put("B-", List.of("O-", "B-"));
        compatibilityMap.put("B+", List.of("O-", "O+", "B-", "B+"));
        compatibilityMap.put("AB-", List.of("O-", "A-", "B-", "AB-"));
        compatibilityMap.put("AB+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));

        return compatibilityMap.getOrDefault(recipientBloodType, new ArrayList<>());
    }

    private UserDto convertToDto(UserEntity userEntity) {
        UserDto userDto = new UserDto();
        userDto.setName(userEntity.getFullName());
        userDto.setEmail(userEntity.getEmail());
        userDto.setPhoneNumber(userEntity.getPhoneNumber());
        userDto.setAddress(userEntity.getAddress());
        userDto.setBloodType(userEntity.getBloodType());

        return userDto;
    }
}
