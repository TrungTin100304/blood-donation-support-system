package com.example.blood_donation_support_system.dto;

import com.example.blood_donation_support_system.entity.ArticleEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDto {
    private String title;
    private String content;
    private String author;
    private LocalDateTime publishDate;
    private String category;
}
