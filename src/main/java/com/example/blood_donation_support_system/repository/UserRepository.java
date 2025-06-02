package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.UserEntity;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findFirstByUserName(String userName);
    boolean existsByUserName(String UserName);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByUserName(String userName);
    List<UserEntity> findByBloodTypeIn(List<String> bloodTypes);

    @Query("SELECT u FROM UserEntity u WHERE NOT EXISTS (SELECT dh FROM DonationHistoryEntity dh WHERE dh.user.userId = u.userId)")
    List<UserEntity> findUsersWithNoDonationHistory();
}
