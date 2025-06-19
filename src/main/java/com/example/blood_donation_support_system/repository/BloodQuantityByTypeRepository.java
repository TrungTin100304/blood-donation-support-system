package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BloodQuantityByTypeRepository  {
    String getBloodType();
    Integer getTotalQuantity();
//    String getHospitalName();
}
