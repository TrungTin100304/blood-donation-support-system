package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodUnitRepository extends JpaRepository<BloodUnitEntity, Integer> {
    boolean existsByUserId_UserId(int userId); // Sử dụng userId_UserId
}