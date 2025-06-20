package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistoryEntity, Integer> {
    List<DonationHistoryEntity> findByUserUserIdOrderByDonationDateDesc(Integer userId);
    Page<DonationHistoryEntity> findByRecoveryStatus(String recoveryStatus, Pageable pageable);
    @Query("SELECT COUNT(dh) FROM DonationHistoryEntity dh WHERE dh.recoveryStatus = :status")
    long countByRecoveryStatus(String status);

    List<DonationHistoryEntity> findByRecoveryStatus(String status);
}