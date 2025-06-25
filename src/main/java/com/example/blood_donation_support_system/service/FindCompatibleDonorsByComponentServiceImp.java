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
    public List<String> findCompatibleTypesByComponent(String recipentBloodType, String component) {
        recipentBloodType = recipentBloodType.trim().toUpperCase();
        component = component.trim().toUpperCase();

        return getCompatibleTypesByComponent(recipentBloodType, component);

    }

    private List<String> getCompatibleTypesByComponent(String recipient, String component) {
        Map<String, List<String>> compatibilityMap = new HashMap<>();

        switch (component) {
            case "WHOLE":
            case "RBC": // Hồng cầu
                compatibilityMap.put("O-", List.of("O-"));
                compatibilityMap.put("O+", List.of("O-", "O+"));
                compatibilityMap.put("A-", List.of("O-", "A-"));
                compatibilityMap.put("A+", List.of("O-", "O+", "A-", "A+"));
                compatibilityMap.put("B-", List.of("O-", "B-"));
                compatibilityMap.put("B+", List.of("O-", "O+", "B-", "B+"));
                compatibilityMap.put("AB-", List.of("O-", "A-", "B-", "AB-"));
                compatibilityMap.put("AB+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
                break;

            case "PLASMA": // Huyết Tương
                // Ngược ABO, Rh vẫn cần tương thích
                compatibilityMap.put("O-", List.of("O-"));
                compatibilityMap.put("O+", List.of("O-", "O+"));
                compatibilityMap.put("A-", List.of("O-", "A-"));
                compatibilityMap.put("A+", List.of("O-", "O+", "A-", "A+"));
                compatibilityMap.put("B-", List.of("O-", "B-"));
                compatibilityMap.put("B+", List.of("O-", "O+", "B-", "B+"));
                compatibilityMap.put("AB-", List.of("O-", "A-", "B-", "AB-"));
                compatibilityMap.put("AB+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
                break;

            case "PLATELET": // Tiểu cầu
                // Linh hoạt, ưu tiên cùng nhóm, chấp nhận tương thích mở rộng nếu xét nghiệm chéo cho phép
                compatibilityMap.put("O-", List.of("O-"));
                compatibilityMap.put("O+", List.of("O-", "O+"));
                compatibilityMap.put("A-", List.of("O-", "A-"));
                compatibilityMap.put("A+", List.of("O-", "O+", "A-", "A+"));
                compatibilityMap.put("B-", List.of("O-", "B-"));
                compatibilityMap.put("B+", List.of("O-", "O+", "B-", "B+"));
                compatibilityMap.put("AB-", List.of("O-", "A-", "B-", "AB-"));
                compatibilityMap.put("AB+", List.of("O-", "O+", "A-", "A+", "B-", "B+", "AB-", "AB+"));
                break;

            default:
                return new ArrayList<>();
        }

        return compatibilityMap.getOrDefault(recipient, new ArrayList<>());
    }

}
