package com.example.blood_donation_support_system.service.user;

import com.example.blood_donation_support_system.dto.DonationHistoryDto;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.repository.DonationHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DonationHistoryServiceImp implements DonationHistoryService{

        @Autowired
        private DonationHistoryRepository donationHistoryRepository;

        @Override
        public List<DonationHistoryDto> getDonationHistoryByUserId(int userId) {
            List<DonationHistoryEntity> donations = donationHistoryRepository.findByUserUserIdOrderByDonationDateDesc(userId);
            return donations.stream().map(this::convertToDto).collect(Collectors.toList());
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
