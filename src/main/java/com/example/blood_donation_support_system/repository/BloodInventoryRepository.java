package com.example.blood_donation_support_system.repository;


import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventoryEntity, Integer> {
    List<BloodInventoryEntity> findByHospitalHospitalId(Integer hospitalId);
    Optional<BloodInventoryEntity> findByBloodUnit(BloodUnitEntity  bloodUnitId);
    List<BloodInventoryEntity> findByStatus(BloodInventoryEntity.BloodInventoryStatus status);


    Optional<BloodInventoryEntity> findFirstByStatusAndHospital_HospitalIdAndBloodUnit_BloodTypeAndBloodUnit_ComponentType(BloodInventoryEntity.BloodInventoryStatus status, Integer hospital_hospitalId, String bloodUnit_bloodType, String bloodUnit_componentType);


//    @Query("""
//    SELECT i FROM BloodInventoryEntity i
//    JOIN i.bloodUnit bu
//    JOIN i.hospital h
//    WHERE i.status = 'In_Stock'
//      AND bu.status = 'Available'
//      AND bu.bloodType = :bloodType
//      AND bu.componentType = :componentType
//""")
//    List<BloodInventoryEntity> findMatchingInventory(
//            @Param("bloodType") String bloodType,
//            @Param("componentType") String componentType
//    );

}
