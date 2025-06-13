//package com.example.blood_donation_support_system.repository;
//
//import com.example.blood_donation_support_system.entity.BloodUnitEntity;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//@Repository
//public interface BloodUnitRepository extends JpaRepository<BloodUnitEntity, Integer> {
//}



package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BloodUnitRepository extends JpaRepository<BloodUnitEntity, Integer> {
////    @Query("SELECT b FROM BloodUnitEntity b LEFT JOIN FETCH b.user LEFT JOIN FETCH b.bloodInventory bi LEFT JOIN FETCH bi.hospital")
//    List<BloodUnitEntity> findAll();
}