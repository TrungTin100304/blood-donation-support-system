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

//    @Column(name = "component_type", length = 10)
//    private String componentType;

    @Column(name = "gender", length = 10)
    private String gender;

    @Column(name = "year_of_birth")
    private Integer yearOfBirth;

    @Column(name = "ready_time")

    private LocalDateTime readyTime;


    @Column(name = "avatar")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id") // khóa ngoại trong bảng user
    private RoleEntity roleEntity;

    @OneToMany(mappedBy = "userEntity")
    private List<ArticleEntity> articleEntities;

    @OneToMany(mappedBy = "requesterId")
    private List<EmergencyEntity> emergencyEntities;


    @OneToMany(mappedBy = "userId")
    private List<BloodUnitEntity> bloodUnits;


}
