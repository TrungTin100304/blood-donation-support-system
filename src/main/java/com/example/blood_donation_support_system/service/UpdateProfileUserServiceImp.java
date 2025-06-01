package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.entity.UserEntity;
import com.example.blood_donation_support_system.repository.UserRepository;
import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.response.UpdateResultResponse;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class UpdateProfileUserServiceImp implements UpdateProfileUserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UploadFileService uploadFileService;

    @Value("${spring.upload.path}")
    private String uploadPath;

    @Override
    public UpdateResultResponse updateProfile(String userName, UserRequest userRequest, MultipartFile avatarFile) {
        Optional<UserEntity> userOpt = userRepository.findByUserName(userName);
        if (!userOpt.isPresent()) {
            return new UpdateResultResponse(false, "User not found");
        }

        UserEntity userEntity = userOpt.get();

        Optional<UserEntity> emailOpt = userRepository.findByEmail(userRequest.getEmail());
        // Kiểm tra email đã tồn tại và không phải của chính user đang đăng nhập
        if (emailOpt.isPresent() && !emailOpt.get().getUserName().equals(userName)) {
            return new UpdateResultResponse(false, "Email already exists");
        }
        userEntity.setAddress(userRequest.getAddress());
        userEntity.setPhoneNumber(userRequest.getPhoneNumber());
        userEntity.setEmail(userRequest.getEmail());

        // Xử lý lưu ảnh nếu có
        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String fileName = uploadFileService.uploadFile(avatarFile);
                String avatarPath = uploadPath + "/" + fileName;
                userEntity.setAvatar(avatarPath);
            } catch (FileUploadException e) {
                e.printStackTrace();
                return new UpdateResultResponse(false, "File upload failed");
            }
        }
        userRepository.save(userEntity);
        return new UpdateResultResponse(true, "Update profile successfully");
    }


}

