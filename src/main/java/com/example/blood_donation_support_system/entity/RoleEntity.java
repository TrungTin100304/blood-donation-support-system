    package com.example.blood_donation_support_system.entity;

    import jakarta.persistence.*;
    import lombok.Data;

    import java.util.List;

    @Data
    @Entity(name = "role")
    public class RoleEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name="role_id")
        private int roleID;
        @Column(name="role_name")
        private String roleName;

        @OneToMany(mappedBy = "roleEntity") // private RoleEntity roleEntity;
        private List<UserEntity> userEntities;

    }
