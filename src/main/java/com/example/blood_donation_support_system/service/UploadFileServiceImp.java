package com.example.blood_donation_support_system.service;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service
public class UploadFileServiceImp implements UploadFileService{
    @Value("${spring.upload.path}")
    private String uploadPath;
    @Override
    public String uploadFile(MultipartFile files) throws FileUploadException {
        try{
            Path root = Paths.get(uploadPath);
            System.out.println(root);
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
            Path filePath = root.resolve(Objects.requireNonNull(files.getOriginalFilename()));
            Files.copy(files.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.getFileName().toString();
        } catch(Exception e) {
            throw new FileUploadException("Cannot upload file");
        }
    }
}
