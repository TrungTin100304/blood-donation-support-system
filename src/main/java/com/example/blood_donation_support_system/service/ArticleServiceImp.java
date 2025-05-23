package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.dto.ArticleDto;
import com.example.blood_donation_support_system.entity.ArticleEntity;
import com.example.blood_donation_support_system.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public List<ArticleDto> getLatestArticles() {

        List<ArticleEntity> articles = articleRepository.findTop5ByOrderByPublishDateDesc();
        List<ArticleDto> dtos = new ArrayList<>();
        for(ArticleEntity article : articles){
            ArticleDto dto = this.convertToDto(article);
            dtos.add(dto);
        }
        return dtos;


        //List<ArticleDto> dtos = articles.stream()
        //    .map(article -> this.convertToDto(article))
        //    .collect(Collectors.toList());

        //return articles.stream()
        //        .map(this::convertToDto)
        //        .collect(Collectors.toList());
    }

    @Override
    public List<ArticleDto> getArticlesByCategory(String category) {
        List<ArticleEntity> articles = articleRepository.findByCategory(category);
        List<ArticleDto> dtos = new ArrayList<>();
        for(ArticleEntity article : articles){
            ArticleDto dto = this.convertToDto(article);
            dtos.add(dto);
        }
        return dtos;
        //return articles.stream()
        //        .map(this::convertToDto)
    }



    private ArticleDto convertToDto(ArticleEntity articleEntity) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setTitle(articleEntity.getTitle());
        articleDto.setContent(articleEntity.getContent());
        articleDto.setPublishDate(articleEntity.getPublishDate());
        articleDto.setCategory(articleEntity.getCategory());

        if (articleEntity.getUserEntity() != null) {
            articleDto.setAuthor(articleEntity.getUserEntity().getHoTen());
        }

        return articleDto;
    }
}
