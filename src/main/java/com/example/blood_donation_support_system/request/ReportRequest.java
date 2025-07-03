package com.example.blood_donation_support_system.request;

import lombok.Data;

@Data
public class ReportRequest {
    private String reportType; // Loại báo cáo: BLOOD_INVENTORY, EMERGENCY_REQUEST, DONATION_HISTORY
    private String startDate; // Định dạng: yyyy-MM-dd
    private String endDate; // Định dạng: yyyy-MM-dd
    private Integer userId; // ID người yêu cầu báo cáo
    private String title; // Tiêu đề báo cáo
}