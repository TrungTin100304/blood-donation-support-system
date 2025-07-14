package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.dto.UserDto;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationHistoryServiceImp implements DonationHistoryService{

        @Autowired
        private DonationHistoryRepository donationHistoryRepository;

        @Autowired
        private UserRepository userRepository;

        @Override
        public List<DonationHistoryDto> getDonationHistoryByUserName(String userName) {
            List<DonationHistoryEntity> donations = donationHistoryRepository.findByUserUserNameOrderByDonationDateDesc(userName);
            return donations.stream().map(this::convertToDto).collect(Collectors.toList());
        }



    @Override
    public ResponseEntity<List<DonationHistoryDto>> getCurrentUserHistoryDonor(int userId) {
        // Tìm UserEntity theo userId
        UserEntity user = userRepository.findById(userId)
                .orElse(null);


        // Tìm tất cả DonationHistoryEntity theo user
        List<DonationHistoryEntity> donationHistoryEntities = donationHistoryRepository.findByUser(user);

        // Chuyển đổi sang DonationHistoryDto
        List<DonationHistoryDto> donationHistoryDtos = donationHistoryEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(donationHistoryDtos);
    }

    @Override
    public List<DonationHistoryDto> getAllDonationHistoryDtos() {
        List<DonationHistoryEntity> donations = donationHistoryRepository.findAll();
        return donations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private DonationHistoryDto convertToDto(DonationHistoryEntity entity) {
            DonationHistoryDto dto = new DonationHistoryDto();
            dto.setHistoryId(entity.getHistoryId());
            dto.setUserId(entity.getUser().getUserId());
            dto.setUserName(entity.getUser().getUserName());
            dto.setDonationDate(entity.getDonationDate());
            dto.setRecoveryStatus(entity.getRecoveryStatus());
            dto.setRecoveryTime(entity.getRecoveryTime());
            if (entity.getBloodUnit() != null) {
                BloodUnitEntity bloodUnit = entity.getBloodUnit();
                dto.setBloodUnitId(bloodUnit.getBloodUnitId());
                dto.setBloodType(bloodUnit.getBloodType());
            } else {
                dto.setBloodUnitId(null);
                dto.setBloodType(null);
            }
            return dto;
        }
}
