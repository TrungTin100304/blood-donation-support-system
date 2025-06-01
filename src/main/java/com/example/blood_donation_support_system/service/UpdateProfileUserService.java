package com.example.blood_donation_support_system.service;

import com.example.blood_donation_support_system.request.UserRequest;
import com.example.blood_donation_support_system.response.UpdateResultResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UpdateProfileUserService {
    UpdateResultResponse updateProfile(String userName, UserRequest userRequest, MultipartFile file);
}
