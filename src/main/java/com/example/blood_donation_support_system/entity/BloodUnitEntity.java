//package com.example.blood_donation_support_system.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Data
//@Entity
//@Table(name = "blood_unit")
//public class BloodUnitEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "blood_unit_id")
//    private Integer bloodUnitId;
//
//    @Column(name = "blood_type", nullable = false, length = 3)
//    private String bloodType;
//
//    @Column(name = "component_type", nullable = false, length = 20)
//    private String componentType;
//
//    @ManyToOne
//    @JoinColumn(name= "user_id")
//    private UserEntity userId;
//
//    @OneToOne(mappedBy = "bloodUnit", cascade = CascadeType.ALL)
//    private BloodInventoryEntity bloodInventoryEntity;
//}


    package com.example.blood_donation_support_system.entity;

    import jakarta.persistence.*;
    import lombok.Data;


    import java.time.LocalDate;

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

        @Column(name = "component_type", length = 20) // Cho phép NULL như DB
        private String componentType;

        @Column(name = "quantity", nullable = false)
        private double quantity; // Thêm quantity

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id")
        private UserEntity user;

        @Column(name = "received_date")
        private LocalDate receivedDate;

        @Column(name = "expiry_date")
        private LocalDate expiryDate; // Thêm expiry_date

        @OneToOne(mappedBy = "bloodUnit", cascade = CascadeType.ALL)
        private BloodInventoryEntity bloodInventory; // Đổi tên biến
}