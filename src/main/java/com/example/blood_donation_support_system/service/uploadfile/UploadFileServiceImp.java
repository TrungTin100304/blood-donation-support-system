package com.example.blood_donation_support_system.service.uploadfile;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileServiceImp implements UploadFileService{
    @Value("${spring.upload.path}")
    private String uploadPath;
    @Override
    public String uploadFile(MultipartFile files, String uploadDirPath) throws FileUploadException {
        try {
            String fileName = files.getOriginalFilename();
            Path uploadDir = Paths.get(uploadDirPath).toAbsolutePath();
            Files.createDirectories(uploadDir); // Tạo thư mục nếu chưa tồn tại
            Path filePath = uploadDir.resolve(fileName);
            files.transferTo(filePath.toFile());
            return fileName;
        } catch (IOException e) {
            throw new FileUploadException("Upload failed", e);
        }
    }
}
