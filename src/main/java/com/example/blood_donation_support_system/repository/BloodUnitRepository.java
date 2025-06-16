package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloodUnitRepository extends JpaRepository<BloodUnitEntity, Integer> {

}