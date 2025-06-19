
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
        IN_STOCK, USED, EXPIRED // Khớp với DB
    }
}