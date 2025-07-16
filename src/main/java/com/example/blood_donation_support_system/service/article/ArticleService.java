package com.example.blood_donation_support_system.service.article;

import com.example.blood_donation_support_system.dto.ArticleDto;
import com.example.blood_donation_support_system.entity.ArticleEntity;
import com.example.blood_donation_support_system.request.ArticleRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ArticleService {
    List<ArticleDto> getLatestArticles();
    List<ArticleDto> getArticlesByCategory(String category);

    void saveArticle(ArticleRequest articleRequest, Integer authorId, MultipartFile file);
}
