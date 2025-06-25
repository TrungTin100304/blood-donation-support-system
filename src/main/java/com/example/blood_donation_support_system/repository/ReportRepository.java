package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.ReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface ReportRepository extends JpaRepository<ReportEntity, Integer> {

    // Tìm báo cáo theo trạng thái
    List<ReportEntity> findByStatus(String status);

    // Tìm báo cáo theo userId
    List<ReportEntity> findByUserId_UserId(Integer userId);

    // Tìm báo cáo theo loại báo cáo
    List<ReportEntity> findByReportType(String reportType);

    // Tìm báo cáo theo khoảng thời gian tạo
    List<ReportEntity> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Tìm báo cáo theo userId và trạng thái
    List<ReportEntity> findByUserId_UserIdAndStatus(Integer userId, String status);

    // Tìm báo cáo theo loại báo cáo và khoảng thời gian
    List<ReportEntity> findByReportTypeAndCreatedDateBetween(String reportType, LocalDateTime startDate, LocalDateTime endDate);
}