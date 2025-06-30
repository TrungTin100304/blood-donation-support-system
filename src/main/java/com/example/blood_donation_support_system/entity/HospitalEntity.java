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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name= "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "hospital") // "hospital" là tên field trong BloodInventoryEntity
    private List<BloodInventoryEntity> bloodInventories;


    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<EmergencyEntity> emergencies;


}