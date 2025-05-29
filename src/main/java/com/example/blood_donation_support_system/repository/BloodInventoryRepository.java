package com.example.blood_donation_support_system.repository;


import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventoryEntity, Integer> {
    List<BloodInventoryEntity> findByHospitalHospitalId(Integer hospitalId);
    Optional<BloodInventoryEntity> findByBloodUnitId(Integer bloodUnitId);
    List<BloodInventoryEntity> findByStatus(BloodInventoryEntity.BloodInventoryStatus status);
}
