package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.EmergencyEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmergencyRepository extends JpaRepository<EmergencyEntity, Integer> {
    Optional<EmergencyEntity> findTopByRequesterOrderByCreatedAtDesc(UserEntity requester);
}
