package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "user")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int maNguoiDung;

    @Column(name = "username")
    private String tenDangNhap;

    @Column(name = "password")
    private String matKhau;

    @Column(name = "full_name",length = 100)
    private String hoTen;

    @Column(name = "google_id", length = 100)
    private String gooleId;

    @Column(name = "login_provider", length = 10)
    private String login_provider;

    @Column(name = "phone_number", length = 15)
    private String soDienThoai;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "address", length = 255)
    private String diaChi;

    @Column(name = "blood_type", length = 5)
    private String nhomMau;

    @Column(name = "ready_time")
    private LocalDateTime thoiGianSanSang;

    @Column(name = "avatar")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id") // khóa ngoại trong bảng user
    private RoleEntity roleEntity;
}
