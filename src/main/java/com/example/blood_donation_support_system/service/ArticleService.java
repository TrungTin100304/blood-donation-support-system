package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.ArticleDto;
import com.example.blood_donation_support_system.entity.ArticleEntity;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> getLatestArticles();
    List<ArticleDto> getArticlesByCategory(String category);
}
