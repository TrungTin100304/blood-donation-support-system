package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name="emergency_request")
public class EmergencyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="request_id")
    private int requestId;
    @Column(name="blood_type")
    private String bloodType;
    @Column(name="component_type")
    private String componentType;
    @Column(name="quantity")
    private double quantity;
    @Column(name="note")
    private String note;
    @Column(name="needed_time")
    private LocalDateTime neededTime;
    @Column(name="created_at")
    private LocalDateTime createdAt;
    @Column(name="status")
    private String status;



    @ManyToOne
    @JoinColumn(name="requester_id")
    private UserEntity requester;

    @ManyToOne
    @JoinColumn(name= "hospital_id")
    private HospitalEntity hospital;
}
