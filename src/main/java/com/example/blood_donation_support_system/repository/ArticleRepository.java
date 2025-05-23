package com.example.blood_donation_support_system.repository;

import com.example.blood_donation_support_system.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<ArticleEntity, Integer> {
    List<ArticleEntity> findTop5ByOrderByPublishDateDesc();

    List<ArticleEntity> findByCategory(String category);
}
