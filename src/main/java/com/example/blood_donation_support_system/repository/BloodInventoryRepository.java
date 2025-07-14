package com.example.blood_donation_support_system.repository;


import com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO;
import com.example.blood_donation_support_system.entity.BloodInventoryEntity;
import com.example.blood_donation_support_system.entity.BloodUnitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BloodInventoryRepository extends JpaRepository<BloodInventoryEntity, Integer> {
    List<BloodInventoryEntity> findByHospitalHospitalId(Integer hospitalId);
    Optional<BloodInventoryEntity> findByBloodUnit(BloodUnitEntity  bloodUnitId);

    List<BloodInventoryEntity> findByHospitalNameContainingIgnoreCase(String name);
    List<BloodInventoryEntity> findByBloodUnitBloodType(String bloodType);

    List<BloodInventoryEntity> findByBloodUnitComponentType(String componentType);
    Optional<BloodInventoryEntity> findByBloodUnitBloodUnitId(int bloodUnitId);
    List<BloodInventoryEntity> findByStatus(BloodInventoryEntity.BloodInventoryStatus status);


    List<BloodInventoryEntity> findByStatusAndHospital_HospitalIdAndBloodUnit_BloodTypeAndBloodUnit_ComponentType(BloodInventoryEntity.BloodInventoryStatus status, Integer hospital_hospitalId, String bloodUnit_bloodType, String bloodUnit_componentType);


//    @Query("""
//    SELECT i FROM BloodInventoryEntity i
//    JOIN i.bloodUnit bu
//    JOIN i.hospital h
//    WHERE i.status = 'In_Stock'
//      AND bu.status = 'Available'
//      AND bu.bloodType = :bloodType
//      AND bu.componentType = :componentType
//""")
//    List<BloodInventoryEntity> findMatchingInventory(
//            @Param("bloodType") String bloodType,
//            @Param("componentType") String componentType
//    );


//    @Query("SELECT bu.bloodType AS bloodType, bu.componentType AS componentType, COALESCE(SUM(bu.quantity), 1.0) AS totalQuantity, h.name AS name " +
//            "FROM BloodUnitEntity bu " +
//            "LEFT JOIN BloodInventoryEntity bi ON bu.bloodUnitId = bi.bloodUnit.bloodUnitId " +
//            "LEFT JOIN HospitalEntity h ON bi.hospital.hospitalId = h.hospitalId " +
//            "WHERE (bi.status = 'IN_STOCK' OR bi.status IS NULL) " +
//            "AND (:hospitalId IS NULL OR bi.hospital.hospitalId = :hospitalId) " +
//            "GROUP BY bu.bloodType, bu.componentType, h.name " +
//            "ORDER BY bu.componentType ASC, bu.bloodType ASC")
//    List<BloodQuantityByTypeRepository> findBloodQuantityByType(@Param("hospitalId") Integer hospitalId);//nên Sửa từ BloodQuantityByTypeRepository thành BloodQuantityByTypeDTO
    //sửa
@Query("SELECT NEW com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO(h.name, bu.bloodType, bu.componentType, CAST(COALESCE(SUM(bu.quantity), 1.0) AS integer)) " +
        "FROM BloodUnitEntity bu " +
        "LEFT JOIN BloodInventoryEntity bi ON bu.bloodUnitId = bi.bloodUnit.bloodUnitId " +
        "LEFT JOIN HospitalEntity h ON bi.hospital.hospitalId = h.hospitalId " +
        "WHERE (bi.status = 'IN_STOCK' OR bi.status IS NULL) " +
        "AND (:hospitalId IS NULL OR bi.hospital.hospitalId = :hospitalId) " +
        "GROUP BY h.name, bu.bloodType, bu.componentType " +
        "ORDER BY bu.componentType ASC, bu.bloodType ASC")
List<BloodQuantityByTypeDTO> findBloodQuantityByType(@Param("hospitalId") Integer hospitalId);


    // Đếm tổng lượng máu theo loại và thành phần (cho dashboard)
    @Query("SELECT NEW com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO('', bu.bloodType, bu.componentType, CAST(COALESCE(SUM(bu.quantity), 0.0) AS integer)) " +
            "FROM BloodUnitEntity bu " +
            "LEFT JOIN BloodInventoryEntity bi ON bu.bloodUnitId = bi.bloodUnit.bloodUnitId " +
            "WHERE bi.status = 'IN_STOCK' " +
            "GROUP BY bu.bloodType, bu.componentType")
    List<BloodQuantityByTypeDTO> findTotalBloodQuantityByTypeAndComponent();

    // Đếm tổng lượng máu theo loại (không phân biệt thành phần)
    @Query("SELECT NEW com.example.blood_donation_support_system.dto.BloodQuantityByTypeDTO(h.name, bu.bloodType, bu.componentType, CAST(COALESCE(SUM(bu.quantity), 0.0) AS integer)) " +
            "FROM BloodUnitEntity bu " +
            "LEFT JOIN BloodInventoryEntity bi ON bu.bloodUnitId = bi.bloodUnit.bloodUnitId " +
            "LEFT JOIN HospitalEntity h ON bi.hospital.hospitalId = h.hospitalId " +
            "WHERE bi.status = 'IN_STOCK' " +
            "GROUP BY h.name, bu.bloodType, bu.componentType")
    List<BloodQuantityByTypeDTO> findTotalBloodQuantityByType();

    // Tìm danh sách tồn kho theo trạng thái và bệnh viện
    List<BloodInventoryEntity> findByStatusAndHospitalHospitalId(BloodInventoryEntity.BloodInventoryStatus status, Integer hospitalId);
    // Thêm phương thức để tìm theo khoảng thời gian lastUpdate
    List<BloodInventoryEntity> findByLastUpdateBetween(LocalDateTime startDate, LocalDateTime endDate);
}
