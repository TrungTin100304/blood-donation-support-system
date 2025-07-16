package com.example.blood_donation_support_system.controller;


import com.example.blood_donation_support_system.dto.ArticleDto;
import com.example.blood_donation_support_system.entity.ArticleEntity;
import com.example.blood_donation_support_system.request.ArticleRequest;
import com.example.blood_donation_support_system.response.BaseResponse;
import com.example.blood_donation_support_system.service.article.ArticleService;
import com.example.blood_donation_support_system.utils.JwtHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/article")

public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private JwtHelper jwtHelper;

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

    @PostMapping(value = "/save", consumes = {"multipart/form-data"})
    public ResponseEntity<?> saveArticle(@ModelAttribute ArticleRequest articleRequest,
                                         @RequestPart("image") MultipartFile image,
                                         @RequestHeader("Authorization") String authId){
            Integer userId = jwtHelper.getUserId(authId);
        try {
             articleService.saveArticle(articleRequest, userId, image);
             BaseResponse response = new BaseResponse();
             response.setCode(200);
             response.setMessage("Save successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Save failed: " + e.getMessage());
        }
    }
}
