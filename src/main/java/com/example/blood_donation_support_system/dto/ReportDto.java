package com.example.blood_donation_support_system.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Integer reportId;
    private String reportType;
    private String title;
    private LocalDateTime createdDate;
    private String data; // Dữ liệu báo cáo dạng JSON
    private String status;
}