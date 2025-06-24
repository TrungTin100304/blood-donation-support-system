package com.example.blood_donation_support_system.service.hospital;

import com.example.blood_donation_support_system.dto.HospitalDto;
import com.example.blood_donation_support_system.entity.HospitalEntity;

import java.util.List;

public interface HospitalService {

    List<HospitalDto> getAllHospitals();
}
