package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findFirstByUserName(String userName);
    boolean existsByUserName(String UserName);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserName(String userName);
    List<UserEntity> findByUserNameContainingIgnoreCase(String userName);
    List<UserEntity> findByBloodTypeIn(List<String> bloodTypes);
    Optional<UserEntity> findByGooleId(String gooleId);
    Optional<UserEntity> findByUserId(int userId);

    @Query("SELECT u FROM UserEntity u WHERE NOT EXISTS (SELECT dh FROM DonationHistoryEntity dh WHERE dh.user.userId = u.userId)")
    List<UserEntity> findUsersWithNoDonationHistory();



    // Người hiến máu
    @Query(value = """
    SELECT * FROM (
        SELECT u.*,
        (6371 * acos(
            cos(radians(:lat)) * cos(radians(u.latitude)) *
            cos(radians(u.longitude) - radians(:lng)) +
            sin(radians(:lat)) * sin(radians(u.latitude))
        )) AS distance
        FROM user u
        JOIN role r ON u.role_id = r.role_id
        WHERE u.latitude IS NOT NULL
          AND u.longitude IS NOT NULL
          AND u.ready_time IS NOT NULL
          AND r.role_name = 'ROLE_MEMBER'
    ) AS subquery
    WHERE subquery.distance <= :maxDistance
    ORDER BY subquery.distance
    """, nativeQuery = true)
    List<UserEntity> findNearbyDonors(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("maxDistance") double maxDistance
    );

    @Query("SELECT u FROM UserEntity u WHERE u.roleEntity.roleName = 'ROLE_MEMBER' AND u.status = 'ACTIVE' AND u.readyTime IS NOT NULL")
    List<UserEntity> findAllDonors();

    // tìm kiếm khoảng cách giữa bệnh viện và hospital
    @Query(value = """
    SELECT u.*
    FROM user u
    JOIN hospital h ON h.hospital_id = :hospitalId
    WHERE u.latitude IS NOT NULL AND u.longitude IS NOT NULL
      AND h.latitude IS NOT NULL AND h.longitude IS NOT NULL
      AND u.status = 'ACTIVE'
      AND (
          6371 * ACOS(
              COS(RADIANS(u.latitude)) * COS(RADIANS(h.latitude)) *
              COS(RADIANS(h.longitude) - RADIANS(u.longitude)) +
              SIN(RADIANS(u.latitude)) * SIN(RADIANS(h.latitude))
          )
      ) <= :maxDistanceKm
    ORDER BY (
        6371 * ACOS(
            COS(RADIANS(u.latitude)) * COS(RADIANS(h.latitude)) *
            COS(RADIANS(h.longitude) - RADIANS(u.longitude)) +
            SIN(RADIANS(u.latitude)) * SIN(RADIANS(h.latitude))
        )
    )
""", nativeQuery = true)
    List<UserEntity> findActiveUsersNearHospital(@Param("hospitalId") Long hospitalId, @Param("maxDistanceKm") Double maxDistanceKm);
}
