package com.example.blood_donation_support_system.service.article;

import com.example.blood_donation_support_system.dto.ArticleDto;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> getLatestArticles();
    List<ArticleDto> getArticlesByCategory(String category);
}
