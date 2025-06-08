package com.example.blood_donation_support_system.service.uploadfile;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.web.multipart.MultipartFile;

public interface UploadFileService {
    String uploadFile(MultipartFile file, String uploadDirPath) throws FileUploadException;
}
