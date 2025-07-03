package com.example.blood_donation_support_system.dto;

import lombok.Data;

import java.util.Map;

@Data
public class DashboardDto {
    private Map<String, Integer> bloodInventoryByType; // Số lượng máu theo loại
    private Map<String, Integer> bloodInventoryByComponent; // Số lượng máu theo thành phần
    private long pendingEmergencyRequests; // Số yêu cầu khẩn cấp đang chờ
    private long matchedEmergencyRequests; // Số yêu cầu khẩn cấp đã khớp
    private long appointmentsToday; // Số lịch hẹn hôm nay
    private long appointmentsThisWeek; // Số lịch hẹn trong tuần
    private long totalDonors; // Tổng số người hiến
    private long totalRecipients; // Tổng số người cần máu
    private long newUsersThisMonth; // Người dùng mới trong tháng
    private long donationsThisMonth; // Số lần hiến máu trong tháng
}