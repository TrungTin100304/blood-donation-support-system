package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity(name="donation_request")
public class DonationRequestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="request_id")
    private Integer requestId;

    @Column(name="required_blood_type")
    private String requiredBloodType;

    @Column(name="required_component_type")
    private String requiredComponentType;

    @Column(name="required_quantity")
    private Integer requiredQuantity;

    @Column(name="status")
    private String status;

    @Column(name="request_date")
    private LocalDateTime requestDate;

    @Column(name="completion_date")
    private LocalDateTime completedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private UserEntity userEntity;
}
