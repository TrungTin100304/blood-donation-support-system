package com.example.blood_donation_support_system.service.hospital;

import com.example.blood_donation_support_system.dto.HospitalDto;
import com.example.blood_donation_support_system.entity.HospitalEntity;
import com.example.blood_donation_support_system.repository.HospitalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalServiceImp implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    @Override
    public List<HospitalDto> getAllHospitals() {
        List<HospitalEntity> hospitalEntity = hospitalRepository.findAll();
        List<HospitalDto> hospitalDto = new ArrayList<>();
        for(HospitalEntity item : hospitalEntity){
            hospitalDto.add(convertHospitalEntityToDto(item));
        }
        return hospitalDto;
    }

    private HospitalDto convertHospitalEntityToDto(HospitalEntity hospitalEntity) {
        HospitalDto hospitalDto = new HospitalDto();
        hospitalDto.setHospitalId(hospitalEntity.getHospitalId());
        hospitalDto.setHospitalName(hospitalEntity.getName());
        hospitalDto.setHospitalAddress(hospitalEntity.getAddress());
        return hospitalDto;
    }
}
