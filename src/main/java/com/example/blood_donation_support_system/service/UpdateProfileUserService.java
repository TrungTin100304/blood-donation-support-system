package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.request.UserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfileUserService {
    boolean updateProfile(String userName, UserRequest userRequest, MultipartFile file);
}
