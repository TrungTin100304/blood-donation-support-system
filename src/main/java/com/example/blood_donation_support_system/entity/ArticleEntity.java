package com.example.blood_donation_support_system.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity(name = "article")
@Data
public class ArticleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="article_id")
    private int articleID;
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "publish_date")
    private LocalDateTime publishDate;
    @Column(name = "category")
    private String category;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity userEntity;


}
