package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;



@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "username" , unique = true)
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "full_name",length = 100)
    private String fullName;

    @Column(name = "google_id", length = 100)
    private String gooleId;

    @Column(name = "login_provider", length = 10)
    private String loginProvider;

    @Column(name = "phone_number", length = 15)
    private String phoneNumber;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "blood_type", length = 5)
    private String bloodType;

    @Column(name = "latitude")
    private double latitude;

    @Column(name= "longitude")
    private double longitude;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "year_of_birth")
    private LocalDate yearOfBirth;

    @Column(name = "ready_time")

    private LocalDateTime readyTime;


    @Column(name = "avatar")
    private String avatar;



    @ManyToOne
    @JoinColumn(name = "role_id") // khóa ngoại trong bảng user
    private RoleEntity roleEntity;

    @OneToMany(mappedBy = "userEntity", cascade = CascadeType.ALL)
    private List<ArticleEntity> articleEntities;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL)
    private List<EmergencyEntity> emergencyEntities;


    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
    private List<BloodUnitEntity> bloodUnits;


}
