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
public class FindCompatibleDonorsByComponentServiceImp implements FindCompatibleDonorsByComponentService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserDto> findCompatibleTypesByComponent(String recipentBloodType, String component) {
        recipentBloodType = recipentBloodType.trim().toUpperCase();
        component = component.trim().toUpperCase();

        List<String> compatibleTypes = getCompatibleTypesByComponent(recipentBloodType, component);
        List<UserEntity> userEntities = userRepository.findByBloodTypeIn(compatibleTypes);
        List<UserDto>  listUserDtos = new ArrayList<>();
        for(UserEntity userEntity : userEntities){
            UserDto userDto = this.convertToDto(userEntity);
            listUserDtos.add(userDto);
        }
        return listUserDtos;
        //return userEntities.stream()
        //        .map(this::convertToDto)
        //        .collect(Collectors.toList());
        //return userEntities.stream()

    }

    private List<String> getCompatibleTypesByComponent(String recipient, String component) {
        Map<String, List<String>> map = new HashMap<>();

        if (component.equals("RBC") || component.equals("WHOLE")) {
            map.put("O-", List.of("O-"));
            map.put("O+", List.of("O-", "O+"));
            map.put("A-", List.of("O-", "A-"));
            map.put("A+", List.of("O-", "O+", "A-", "A+"));
            map.put("B-", List.of("O-", "B-"));
            map.put("B+", List.of("O-", "O+", "B-", "B+"));
            map.put("AB-", List.of("O-", "A-", "B-", "AB-"));
            map.put("AB+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
        } else if (component.equals("PLASMA") || component.equals("PLATELET")) {
            map.put("O-", List.of("O-", "A-", "B-", "AB-"));
            map.put("O+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
            map.put("A-", List.of("A-", "AB-"));
            map.put("A+", List.of("A-", "A+", "AB-", "AB+"));
            map.put("B-", List.of("B-", "AB-"));
            map.put("B+", List.of("B-", "B+", "AB-", "AB+"));
            map.put("AB-", List.of("AB-"));
            map.put("AB+", List.of("AB-", "AB+"));
        }

        return map.getOrDefault(recipient, new ArrayList<>());

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
