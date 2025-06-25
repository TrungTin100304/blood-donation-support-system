package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
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
          AND u.blood_type IN (:bloodType)
    ) AS subquery
    WHERE subquery.distance <= :maxDistance
    ORDER BY subquery.distance
    """, nativeQuery = true)
    List<UserEntity> findNearbyDonors(
            @Param("lat") double lat,
            @Param("lng") double lng,
            @Param("maxDistance") double maxDistance,
            @Param("bloodType") List<String> bloodType
    );


//    // Người cần máu
//    @Query(value = """
//            SELECT u.*,
//                       (6371 * acos(
//                           cos(radians(:lat)) * cos(radians(u.latitude)) *
//                           cos(radians(u.longitude) - radians(:lng)) +
//                           sin(radians(:lat)) * sin(radians(u.latitude))
//                       )) AS distance
//                FROM user u
//                JOIN emergency_request er ON u.user_id  = er.requester_id
//                WHERE u.latitude IS NOT NULL AND u.longitude IS NOT NULL
//                HAVING distance <= :maxDistance
//                ORDER BY distance
//        """, nativeQuery = true)
//    List<UserEntity> findNearbyRecipients(
//            @Param("lat") double lat,
//            @Param("lng") double lng,
//            @Param("maxDistance") double maxDistance
//    );



//    // Đếm số người dùng mới trong tháng hiện tại
//    long countByCreatedAtAfter(LocalDateTime startOfMonth);

    // Đếm tổng số người hiến máu (vai trò ROLE_MEMBER)
    long countByRole_RoleName(String roleName);

    // Đếm số người dùng chưa có lịch sử hiến máu
    long countByUserIdNotIn(List<Integer> userIdsWithDonationHistory);
}
