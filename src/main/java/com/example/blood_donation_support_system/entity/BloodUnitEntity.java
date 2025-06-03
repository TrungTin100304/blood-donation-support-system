package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

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
}