package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;

@Data
@Entity
@Table(name = "blood_unit")
public class BloodUnitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blood_unit_id")
    private Integer bloodUnitId;

    @Column(name = "blood_type", nullable = false, length = 3)
    private String bloodType;

    @Column(name = "component_type", nullable = false, length = 20)
    private String componentType;

    @Column(name="quantity")
    private Integer quantity;


    @Column(name="status")
    private String status;

    @Column(name = "received_date")
    private LocalDate receviedDate;

    @ManyToOne
    @JoinColumn(name= "user_id")
    private UserEntity userId;
}