package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.EmergencyEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EmergencyRepository extends JpaRepository<EmergencyEntity, Integer> {
    Optional<EmergencyEntity> findTopByRequesterAndStatusOrderByCreatedAtDesc(UserEntity requester, String status);

    // Đếm số yêu cầu khẩn cấp theo trạng thái
    long countByStatus(String status);

    // Tìm yêu cầu khẩn cấp theo khoảng thời gian tạo
    List<EmergencyEntity> findByCreatedAtBetween(LocalDateTime startDate, LocalDateTime endDateTime);

    // Tìm yêu cầu khẩn cấp theo người yêu cầu
    List<EmergencyEntity> findByRequester(UserEntity requester);

    // Đếm số người yêu cầu khác nhau
    @Query("SELECT COUNT(DISTINCT e.requester) FROM emergency_request  e")
    long countDistinctByRequester();
}
