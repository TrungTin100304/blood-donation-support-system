package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "blood_inventory")
public class BloodInventoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer inventoryId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blood_unit_id", referencedColumnName = "blood_unit_id", nullable = false, unique = true)
    private BloodUnitEntity bloodUnit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", referencedColumnName = "hospital_id", nullable = false)
//    @Column(name = "hospital_id") // Thêm annotation này để chỉ rõ cột
    private HospitalEntity hospital;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)  //Lưu tên của enum trong DB
    @ColumnDefault("'In Stock'") //Thiết lập giá trị mặc định trong DB
    private BloodInventoryStatus status; //Enum quản lý trạng thái máu

    public enum BloodInventoryStatus {
        In_Stock, Used, Expired // Thay đổi tên enum để khớp với cơ sở dữ liệu
    }

    // Getters, setters (tự động bởi @Data)
}