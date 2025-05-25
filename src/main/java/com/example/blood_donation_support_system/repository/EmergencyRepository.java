package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.EmergencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmergencyRepository extends JpaRepository<EmergencyEntity, Integer> {
}
