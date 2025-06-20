package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.DonationRequestEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DonationRequestRepository extends JpaRepository<DonationRequestEntity,Integer> {
    Optional<DonationRequestEntity> findByUserEntityOrderByRequestDateDesc(UserEntity userEntity);

}
