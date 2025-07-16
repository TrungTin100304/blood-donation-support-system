package com.example.blood_donation_support_system.service.article;

import com.example.blood_donation_support_system.dto.ArticleDto;
import com.example.blood_donation_support_system.entity.ArticleEntity;
import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.ArticleRepository;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.ArticleRequest;
import com.example.blood_donation_support_system.service.uploadfile.UploadFileService;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleServiceImp implements ArticleService{
    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UploadFileService uploadFileService;

    @Value("${spring.upload.path}/articleavatars")
    private String uploadPath;

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
        articleDto.setImgPath( "http://localhost:8080"+ articleEntity.getImagePath());

        if (articleEntity.getUserEntity() != null) {
            articleDto.setAuthor(articleEntity.getUserEntity().getFullName());
        }

        return articleDto;
    }

    @Override
    public void saveArticle(ArticleRequest articleRequest, Integer authorId, MultipartFile avatarFile) {
        UserEntity userEntity = userRepository.findByUserId(authorId).orElseThrow(() -> new UnsupportedOperationException("User not found"));

        ArticleEntity articleEntity = new ArticleEntity();
        articleEntity.setTitle(articleRequest.getTitle());
        articleEntity.setContent(articleRequest.getContent());
        articleEntity.setPublishDate(LocalDateTime.now());
        articleEntity.setCategory(articleRequest.getCategory());
        articleEntity.setUserEntity(userEntity);


        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String fileName = uploadFileService.uploadFile(avatarFile, uploadPath);
                String avatarPath = uploadPath + "/"  + fileName;
                articleEntity.setImagePath(avatarPath);
            } catch (FileUploadException e) {
                throw new UnsupportedOperationException("File upload failed");
            }
        }
         articleRepository.save(articleEntity);
    }
}
