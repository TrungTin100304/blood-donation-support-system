package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name="appointment")
@Data
public class AppointmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Integer appointmentId;

    @Column(name = "appointment_date")
    private LocalDateTime appointmentDate;

    @Column(name = "location")
    private String location;

    @Column(name = "status")
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="donor_id")
    private UserEntity donor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;


}
