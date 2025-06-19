package com.example.blood_donation_support_system.repository;


import com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO;
import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventoryEntity, Integer> {
    List<BloodInventoryEntity> findByHospitalHospitalId(Integer hospitalId);

    List<BloodInventoryEntity> findByHospitalNameContainingIgnoreCase(String name);
    List<BloodInventoryEntity> findByBloodUnitBloodType(String bloodType);
    Optional<BloodInventoryEntity> findByBloodUnitBloodUnitId(int bloodUnitId);
    List<BloodInventoryEntity> findByStatus(BloodInventoryEntity.BloodInventoryStatus status);

    @Query("SELECT bu.bloodType AS bloodType, COALESCE(SUM(bu.quantity), 0.0) AS totalQuantity " +
            "FROM BloodUnitEntity bu " +
            "LEFT JOIN BloodInventoryEntity bi ON bu.bloodUnitId = bi.bloodUnit.bloodUnitId " +
            "WHERE bi.status = 'IN_STOCK' OR bi.status IS NULL " +
            "GROUP BY bu.bloodType " +
            "ORDER BY bu.bloodType")
    List<BloodQuantityByTypeRepository> findBloodQuantityByType();
}

