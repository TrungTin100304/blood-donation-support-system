package com.example.blood_donation_support_system.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Đường dẫn thực tế tới thư mục chứa ảnh
        String uploadPath = Paths.get("upload").toAbsolutePath().toUri().toString();
        System.out.println(Paths.get("upload").toAbsolutePath());
        System.out.println(Paths.get("upload").toAbsolutePath().toUri());
        registry.addResourceHandler("/upload/**")
                .addResourceLocations(uploadPath);
    }
}