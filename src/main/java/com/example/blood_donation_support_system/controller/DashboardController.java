package com.example.blood_donation_support_system.controller;

import com.example.blood_donation_support_system.dto.DashboardDto;
import com.example.blood_donation_support_system.request.ReportRequest;
import com.example.blood_donation_support_system.dto.ReportDto;
import com.example.blood_donation_support_system.service.dashboard.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    // Lấy dữ liệu tổng quan cho dashboard
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<DashboardDto> getDashboard() {
        DashboardDto dashboardData = dashboardService.getDashboardData();
        return ResponseEntity.ok(dashboardData);
    }

    // Tạo báo cáo (nếu đã triển khai)
    @PostMapping("/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ReportDto> generateReport(@RequestBody ReportRequest request) {
        ReportDto report = dashboardService.generateReport(request);
        return ResponseEntity.ok(report);
    }
}