package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.DonationHistoryEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DonationHistoryRepository extends JpaRepository<DonationHistoryEntity, Integer> {
    List<DonationHistoryEntity> findByUserUserNameOrderByDonationDateDesc(String  userName);
    Page<DonationHistoryEntity> findByRecoveryStatus(String recoveryStatus, Pageable pageable);
    @Query("SELECT COUNT(dh) FROM DonationHistoryEntity dh WHERE dh.recoveryStatus = :status")
    long countByRecoveryStatus(String status);

    List<DonationHistoryEntity> findByRecoveryStatus(String status);


    // Đếm số lần hiến máu sau một ngày cụ thể
    long countByDonationDateAfter(LocalDateTime date);

    // Đếm số lần hiến máu trong khoảng thời gian
    long countByDonationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Lấy danh sách lịch sử hiến máu trong khoảng thời gian
    List<DonationHistoryEntity> findByDonationDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Đếm tổng số lần hiến máu theo userId
    long countByUserUserId(Integer userId);

    // Tìm danh sách người dùng có lịch sử hiến máu (distinct userId)
    @Query("SELECT DISTINCT dh.user FROM DonationHistoryEntity dh")
    List<UserEntity> findDistinctUsersWithDonationHistory();
}