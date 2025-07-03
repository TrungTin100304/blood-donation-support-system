package com.example.blood_donation_support_system.service.dashboard;

import com.example.blood_donation_support_system.dto.DashboardDto;
import com.example.blood_donation_support_system.dto.ReportDto;
import com.example.blood_donation_support_system.request.ReportRequest;

public interface DashboardService {
    DashboardDto getDashboardData();
    ReportDto generateReport(ReportRequest request);
}