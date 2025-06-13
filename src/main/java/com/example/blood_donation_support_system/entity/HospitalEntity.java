package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "hospital")
public class HospitalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hospital_id")
    private Integer hospitalId;

    @Column(name = "hospital_name", nullable = false)
    private String hospitalName;

    @Column(name = "address")
    private String address;

    @OneToMany(mappedBy = "hospital") // "hospital" là tên field trong BloodInventoryEntity
    private List<BloodInventoryEntity> bloodInventories;

    // Getters, setters (tự động bởi @Data)
}