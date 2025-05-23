package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findFirstByUserName(String userName);
    boolean existsByUserName(String UserName);
    Optional<UserEntity> findByEmail(String email);
}
