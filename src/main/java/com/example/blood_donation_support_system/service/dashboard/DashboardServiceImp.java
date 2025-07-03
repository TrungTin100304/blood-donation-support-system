package com.example.blood_donation_support_system.service.dashboard;

import com.example.blood_donation_support_system.dto.DashboardDto;
import com.example.blood_donation_support_system.dto.ReportDto;
import com.example.blood_donation_support_system.entity.*;
import com.example.blood_donation_support_system.repository.*;
import com.example.blood_donation_support_system.request.ReportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DashboardServiceImp implements DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardServiceImp.class);

    @Autowired
    private BloodInventoryRepository bloodInventoryRepository;

    @Autowired
    private EmergencyRepository emergencyRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DonationHistoryRepository donationHistoryRepository;

    @Autowired
    private ReportRepository reportRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public DashboardDto getDashboardData() {
        DashboardDto dto = new DashboardDto();

        // Thống kê kho máu theo loại
        List<BloodInventoryEntity> inventories = bloodInventoryRepository.findByStatus(BloodInventoryEntity.BloodInventoryStatus.IN_STOCK);
        Map<String, Integer> bloodByType = inventories.stream()
                .collect(Collectors.groupingBy(
                        inv -> inv.getBloodUnit().getBloodType(),
                        Collectors.summingInt(inv -> inv.getBloodUnit().getQuantity())
                ));
        dto.setBloodInventoryByType(bloodByType);

        // Thống kê kho máu theo thành phần
        Map<String, Integer> bloodByComponent = inventories.stream()
                .collect(Collectors.groupingBy(
                        inv -> inv.getBloodUnit().getComponentType(),
                        Collectors.summingInt(inv -> inv.getBloodUnit().getQuantity())
                ));
        dto.setBloodInventoryByComponent(bloodByComponent);

        // Yêu cầu khẩn cấp
        dto.setPendingEmergencyRequests(emergencyRepository.countByStatus("Pending"));
        dto.setMatchedEmergencyRequests(emergencyRepository.countByStatus("Matched"));

        // Lịch hẹn
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        LocalDateTime endOfWeek = startOfDay.plusDays(7);
        dto.setAppointmentsToday(appointmentRepository.countByAppointmentDateBetween(startOfDay, endOfDay));
        dto.setAppointmentsThisWeek(appointmentRepository.countByAppointmentDateBetween(startOfDay, endOfWeek));

        // Thống kê người dùng
        dto.setTotalDonors(userRepository.countByRoleEntity_RoleName("ROLE_MEMBER")); // Giả sử USER là người hiến
        dto.setTotalRecipients(emergencyRepository.countDistinctByRequester());
        dto.setNewUsersThisMonth(userRepository.countByCreatedAtAfter(LocalDateTime.now().minusMonths(1)));

        // Thống kê hiến máu
        dto.setDonationsThisMonth(donationHistoryRepository.countByDonationDateAfter(LocalDateTime.now().minusMonths(1)));

        logger.info("Generated dashboard data: {}", dto);
        return dto;
    }

    @Override
    public ReportDto generateReport(ReportRequest request) {
        ReportEntity report = new ReportEntity();
        report.setUserId(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found")));
        report.setReportType(request.getReportType());
        report.setTitle(request.getTitle());
        report.setCreatedDate(LocalDateTime.now());
        report.setStatus("GENERATED");

        try {
            String reportData = generateReportData(request);
            report.setData(reportData);
        } catch (Exception e) {
            report.setStatus("FAILED");
            report.setData("Error generating report: " + e.getMessage());
            logger.error("Failed to generate report: {}", e.getMessage());
        }

        report = reportRepository.save(report);

        ReportDto dto = new ReportDto();
        dto.setReportId(report.getReportId());
        dto.setUserId(report.getUserId().getUserId());
        dto.setReportType(report.getReportType());
        dto.setTitle(report.getTitle());
        dto.setCreatedDate(report.getCreatedDate());
        dto.setData(report.getData());
        dto.setStatus(report.getStatus());

        logger.info("Generated report: {}", dto);
        return dto;
    }

    private String generateReportData(ReportRequest request) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(request.getStartDate(), formatter);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = LocalDate.parse(request.getEndDate(), formatter).atTime(23, 59, 59);

        Map<String, Object> reportData = new HashMap<>();

        switch (request.getReportType()) {
            case "BLOOD_INVENTORY":
                List<BloodInventoryEntity> inventories = bloodInventoryRepository
                        .findByLastUpdateBetween(startDateTime, endDateTime);
                Map<String, Integer> bloodByType = inventories.stream()
                        .collect(Collectors.groupingBy(
                                inv -> inv.getBloodUnit().getBloodType(),
                                Collectors.summingInt(inv -> inv.getBloodUnit().getQuantity())
                        ));
                reportData.put("bloodByType", bloodByType);
                reportData.put("totalUnits", inventories.size());
                break;

            case "EMERGENCY_REQUEST":
                List<EmergencyEntity> emergencies = emergencyRepository
                        .findByCreatedAtBetween(startDateTime, endDateTime);
                Map<String, Long> statusCounts = emergencies.stream()
                        .collect(Collectors.groupingBy(EmergencyEntity::getStatus, Collectors.counting()));
                reportData.put("emergencyRequests", statusCounts);
                reportData.put("totalRequests", emergencies.size());
                break;

            case "DONATION_HISTORY":
                List<DonationHistoryEntity> donations = donationHistoryRepository
                        .findByDonationDateBetween(startDateTime, endDateTime);
                Map<String, Long> donationsByBloodType = donations.stream()
                        .collect(Collectors.groupingBy(
                                dh -> dh.getBloodUnit().getBloodType(),
                                Collectors.counting()
                        ));
                reportData.put("donationsByBloodType", donationsByBloodType);
                reportData.put("totalDonations", donations.size());
                break;

            default:
                throw new IllegalArgumentException("Invalid report type: " + request.getReportType());
        }

        return objectMapper.writeValueAsString(reportData);
    }
}