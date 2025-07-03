package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "report")
public class ReportEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Integer reportId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userId;

    @Column(name = "report_type", length = 50, nullable = false)
    private String reportType;

    @Column(name = "created_date", nullable = false)
    private LocalDateTime createdDate;

    @Column(name = "data", columnDefinition = "TEXT")
    private String data;

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "status", length = 20, nullable = false)
    private String status;
}