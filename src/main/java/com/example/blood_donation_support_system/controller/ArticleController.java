package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.dto.ArticleDto;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @GetMapping("/latest")
    public ResponseEntity<?> getLatestArticles(){
        List<ArticleDto> dtos = articleService.getLatestArticles();
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setData(dtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/category")
    public ResponseEntity<?> getArticlesByCategory(@RequestParam String category){
        List<ArticleDto> dtos = articleService.getArticlesByCategory(category);
        BaseResponse response = new BaseResponse();
        response.setCode(200);
        response.setData(dtos);
        return ResponseEntity.ok(response);
    }
}
