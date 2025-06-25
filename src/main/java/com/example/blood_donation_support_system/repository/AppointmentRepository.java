package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {

    // Đếm số lượng lịch hẹn trong khoảng thời gian
    long countByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Đếm số lượng lịch hẹn trong ngày hiện tại
    default long countAppointmentsToday() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfDay = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        return countByAppointmentDateBetween(startOfDay, endOfDay);
    }


    // Đếm số lượng lịch hẹn trong tuần hiện tại
    default long countAppointmentsThisWeek() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfWeek = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
                .withHour(23).withMinute(59).withSecond(59);
        return countByAppointmentDateBetween(startOfWeek, endOfWeek);
    }

    // Đếm số lượng lịch hẹn theo trạng thái
    long countByStatus(String status);

    // Lấy danh sách lịch hẹn trong khoảng thời gian (tùy chọn cho báo cáo)
    List<AppointmentEntity> findByAppointmentDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Đếm số lượng lịch hẹn đã xác nhận trong ngày
    @Query("SELECT COUNT(a) FROM appointment a WHERE a.appointmentDate BETWEEN :startOfDay AND :endOfDay AND a.status = 'Confirmed'")
    long countConfirmedAppointmentsToday(LocalDateTime startOfDay, LocalDateTime endOfDay);
}
