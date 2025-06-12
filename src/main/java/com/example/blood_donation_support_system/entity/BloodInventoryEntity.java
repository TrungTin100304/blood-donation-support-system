//package com.example.blood_donation_support_system.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.ColumnDefault;
//
//import java.time.LocalDateTime;
//
//
//@Data
//@Entity
//@Table(name = "blood_inventory")
//public class BloodInventoryEntity {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "inventory_id")
//    private Integer inventoryId;
//
//    @OneToOne
//    @JoinColumn(name = "blood_unit_id", unique = true)
//    private BloodUnitEntity bloodUnitId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "hospital_id", referencedColumnName = "hospital_id", nullable = false)
////    @Column(name = "hospital_id") // Thêm annotation này để chỉ rõ cột
//    private HospitalEntity hospital;
//
//    @Column(name = "last_update")
//    private LocalDateTime lastUpdate;
//
//    @Column(name = "status", nullable = false)
//    @Enumerated(EnumType.STRING)  //Lưu tên của enum trong DB
//    @ColumnDefault("'In Stock'") //Thiết lập giá trị mặc định trong DB
//    private BloodInventoryStatus status; //Enum quản lý trạng thái máu
//
//    public enum BloodInventoryStatus {
//        In_Stock, Used, Expired // Thay đổi tên enum để khớp với cơ sở dữ liệu
//    }
//
//    // Getters, setters (tự động bởi @Data)
//}



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

    @OneToOne
    @JoinColumn(name = "blood_unit_id", unique = true, nullable = false) // NOT NULL để khớp DB
    private BloodUnitEntity bloodUnit; // Đổi tên biến cho rõ ràng

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id", nullable = false)
    private HospitalEntity hospital;

    @Column(name = "last_update")
    @ColumnDefault("CURRENT_TIMESTAMP")
    private LocalDateTime lastUpdate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'IN_STOCK'")
    private BloodInventoryStatus status;

    public enum BloodInventoryStatus {
        In_Stock, Used, Expired // Khớp với DB
    }
}