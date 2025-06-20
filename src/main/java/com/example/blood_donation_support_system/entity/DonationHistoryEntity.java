package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "donation_history")
public class DonationHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private int historyId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(name = "donation_date", nullable = false)
    private LocalDateTime donationDate;

    @ManyToOne
    @JoinColumn(name = "blood_unit_id", nullable = false)
    private BloodUnitEntity bloodUnitId;

    @Column(name = "recovery_time")
    private LocalDate recoveryTime;

    @Column(name = "recovery_status", nullable = false, length = 20)
    private String recoveryStatus = "RECOVERING";

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}